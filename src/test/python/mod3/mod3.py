#!/usr/bin/env pyton
# -- coding:utf-8 --

# import sys
# sys.path.append("..")
# import mod2


def sayHello():
    aDict = {'key1': 'value1', 'key2': 'value2', 'key3': 'value3'}
    print '-----------dict-------------'
    for d in aDict:
        print "%s:%s" % (d, aDict[d])

    print '-----------items-------------'
    for (k, v) in aDict.items():
        print '%s:%s' % (k, v)

    # 效率最高
    print '------------iteritems---------'
    for k, v in aDict.iteritems():
        print '%s:%s' % (k, v)

    # 最笨的方法
    print '---------iterkeys---------------'
    for k in aDict.iterkeys():
        print '%s:%s' % (k, aDict[k])

    print '------------iterkeys, itervalues----------'
    for k, v in zip(aDict.iterkeys(), aDict.itervalues()):
        print '%s:%s' % (k, v)


def add(x, y, f):
    return f(x) + f(y)


def f(x):
    return x * x * x


if __name__ == "__main__":
    # num = add(1, 2, lambda x: x * x)
    # num = add(1, 2, f)
    # print num

    arr = [1, 2, 3]
    print map(lambda x: x * x, arr)

    # reduce必须要俩参数
    print reduce(lambda x, y: x + y, arr)

    # 将全数字字符串转换为数字
    print reduce(lambda x, y: 10 * x + y,map(lambda x:{'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[x],"2212x"))

    # 删选选取三倍的数字
    print filter(lambda x: x % 3 == 0, [1, 2, 3, 4, 5, 6])


    # 去掉列表中空格
    # int没有strip方法
    print filter(lambda x: x and x.strip(), ["sadd", "sadasd21", None, "dfgdfg", ' ', "qwefs",''])


    print sorted(['bob', 'about', 'Zoo', 'Credit'])



    # sayHello()
