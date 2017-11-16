#!/usr/bin/env pyton
# -- coding:utf-8 --

# from os.path import join as xx
#
# import mod1
# # import mod2.mod2 as xx
# # from mod2.mod2 import sayHello
# from mod2.mod2 import sayHello as sayHello11
# from mod3.mod3 import sayHello
#
# print mod1.__name__
#
# mod1.sayHello()
#
# print(xx('C:\\windows', 'system32'))
# sayHello11()
#
# sayHello()

# import os
# print(os.path.join('C:\\windows', 'system32'))
#
# import os.path
# print(os.path.join('C:\\windows', 'system32'))


def funA(arg):
    print 'A'


@funA
def funB():
    print 'B'

# funA(funB())
