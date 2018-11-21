#!/usr/bin/env python

from setuptools import setup, find_packages

description = "Python SDK for Bhex REST API (https://www.bhex.com)"

setup(
    name="bhex",
    version="1.0.1",
    author="Bhex",
    author_email="pip@bhex.com",
    description=description,
    long_description=description,
    long_description_content_type="text/markdown",
    url="https://github.com/bhexofficial/bhex-python",
    packages=find_packages(),
    install_requires=['requests', 'six'],
    classifiers=[
        "Programming Language :: Python :: 3",
        "License :: OSI Approved :: MIT License",
        "Operating System :: OS Independent",
    ],
)