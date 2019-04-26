#!/usr/bin/env python
import codecs
import os

from setuptools import setup, find_packages

description = "Python SDK for Bhex REST And Websocket API (https://www.bhex.com)"

here = os.path.abspath(os.path.dirname(__file__))


def read(*parts):
    with codecs.open(os.path.join(here, *parts), 'r') as fp:
        return fp.read()


setup(
    name="bhex",
    version="1.1",
    author="Bhex",
    author_email="pip@bhex.com",
    description=description,
    long_description=read('README.md'),
    long_description_content_type="text/markdown",
    url="https://github.com/bhexopen/BHEX-OpenApi/tree/master/sdk/python",
    packages=find_packages(),
    install_requires=['requests', 'six', 'twisted', 'autobahn', 'pyopenssl', 'service_identity'],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
)