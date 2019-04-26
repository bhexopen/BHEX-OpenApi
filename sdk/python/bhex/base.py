import hashlib
import hmac
import time
import urllib

import requests
import six

from . exceptions import BhexAPIException, BhexRequestException


class Request(object):

    API_VERSION = 'v1'
    QUOTE_API_VERSION = 'v1'

    def __init__(self, api_key, secret, entry_point='https://api.bhex.com/openapi/', proxies=None):

        if not entry_point.endswith('/'):
            entry_point = entry_point + '/'
        self.api_key = api_key
        self.secret = secret
        self.entry_point = entry_point
        self.proxies = proxies
        self.ping()

    def _create_api_uri(self, path, version):
        version_path = ''
        if version:
            version_path = version + '/'
        return self.entry_point + version_path + path

    def _create_quote_api_uri(self, path, version):
        return self.entry_point + 'quote/' + version + '/' + path

    def _generate_signature(self, data):

        if six.PY2:
            params_str = urllib.urlencode(data)
        else:
            params_str = urllib.parse.urlencode(data)

        digest = hmac.new(self.secret.encode(encoding='UTF8'),
                          params_str.encode(encoding='UTF8'),
                          digestmod=hashlib.sha256).hexdigest()
        return digest

    def _get(self, path, signed=False, version=API_VERSION, **kwargs):
        uri = self._create_api_uri(path, version)
        return self._request('GET', uri, signed, **kwargs)

    def _post(self, path, signed=False, version=API_VERSION, **kwargs):
        uri = self._create_api_uri(path, version)
        return self._request('POST', uri, signed, **kwargs)

    def _put(self, path, signed=False, version=API_VERSION, **kwargs):
        uri = self._create_api_uri(path, version)
        return self._request('PUT', uri, signed, **kwargs)

    def _delete(self, path, signed=False, version=API_VERSION, **kwargs):
        uri = self._create_api_uri(path, version)
        return self._request('DELETE', uri, signed, **kwargs)

    def _quote_get(self, path, signed=False, version=QUOTE_API_VERSION, **kwargs):
        uri = self._create_quote_api_uri(path, version)
        return self._request('GET', uri, signed, **kwargs)

    def _request(self, method, uri, signed, **kwargs):

        kwargs['timeout'] = 10

        date_type = 'data' if method == 'POST' else 'params'
        if date_type not in kwargs:
            kwargs[date_type] = {}

        kwargs[date_type]['timestamp'] = int(time.time() * 1000)
        if signed:
            kwargs[date_type]['signature'] = self._generate_signature(kwargs[date_type])

        kwargs['headers'] = {
            'X-BH-APIKEY': self.api_key,
            'User-Agent': 'Bhex-P 1.0'
        }

        response = requests.request(method, uri, proxies=self.proxies, **kwargs)
        return self._handle_response(response)

    def _handle_response(self, response):

        if not str(response.status_code).startswith('2') and not response.status_code == 400:
            raise BhexAPIException(response)
        try:
            return response.json()
        except ValueError:
            raise BhexRequestException('Invalid Response: %s' % response.text)

    def ping(self):
        return self._get('ping')

    def time(self):
        """
        Check server time
        """
        return self._get('time')

    def broker_info(self):
        """
        Broker information
        """
        return self._get('brokerInfo')

    def stream_get_listen_key(self):
        """
        Start user data stream (USER_STREAM)
        """
        return self._post('userDataStream', signed=True)

    def stream_keepalive(self, listen_key):
        """
        Keepalive user data stream (USER_STREAM)
        """
        params = {
            'listenKey': listen_key
        }
        return self._put('userDataStream', signed=True, params=params)

    def stream_close(self, listen_key):
        """
        Close user data stream (USER_STREAM)
        """
        params = {
            'listenKey': listen_key
        }
        return self._delete('userDataStream', signed=True, params=params)
