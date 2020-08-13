-- 尽可能多的取出zset数据，数据字节数小于size，至少取出一条
local key = KEYS[1]
local size = ARGV[1]

local list = redis.call("zrange", key, 0, -1)
local result = {}
for i = 1, #list do
    if (i == 1 or size - #list[i] >= 0) then
        size = size - #list[i]
        redis.call("zrem", key, list[i])
        table.insert(result, list[i])
    end
end
return result