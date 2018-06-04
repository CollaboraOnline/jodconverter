#!/usr/bin/env python3

import requests

url = "https://localhost:9980/lool/convert-to/pdf"
files = {"file": open("/path/to/test.txt", 'rb')}
outFile = open("/path/to/test.pdf", "wb")
response = requests.post(url, files=files, verify=False)
outFile.write(response.content)

# vim:set shiftwidth=4 softtabstop=4 expandtab:
