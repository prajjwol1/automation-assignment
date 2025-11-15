#!/usr/bin/env python3
import sys

try:
    lines = []
    for i in range(2):
        lines.append(sys.stdin.readline().strip())
    a = float(lines[0])
    b = float(lines[1])
    res = a + b
    # print as integer if both are integers
    if a.is_integer() and b.is_integer():
        print(int(res))
    else:
        print(res)
except Exception:
    print('ERROR')
