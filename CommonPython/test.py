price = {
    "huawei": 2499,
    "xiaomi": 1999,
    "apple":  6399
}

inc = {}
cny = dict()

print (price["xiaomi"])
print (price.get("xiaomi1", 12))

keys = list(price)
del price["xiaomi"]