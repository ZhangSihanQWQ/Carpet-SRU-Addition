# 以游戏总时间按 24 小时服务器运行时间换算签到日，避免每 5 分钟/每个游戏日重复签到。
execute unless score #ticks_per_day sru_ci_tmp matches 1.. run scoreboard players set #ticks_per_day sru_ci_tmp 1728000
execute store result score #current_day sru_ci_tmp run time query gametime
scoreboard players operation #current_day sru_ci_tmp /= #ticks_per_day sru_ci_tmp
scoreboard players add #current_day sru_ci_tmp 1
scoreboard players operation #current_day sru_ci_tmp += #day_offset sru_ci_tmp

# 新的一天：只初始化当天状态和当天移动基线，不清空累计签到天数。
execute as @a unless score @s sru_ci_lastday = #current_day sru_ci_tmp run function sru_checkin:player_init

# 判定条件是“或”：当天在线满 5 分钟，或当天移动距离超过 200 米，二者任一满足即签到。
scoreboard players add @a[scores={sru_ci_today=0}] sru_ci_ticks 1
execute as @a[scores={sru_ci_today=0,sru_ci_ticks=6000..}] run function sru_checkin:success
execute as @a[scores={sru_ci_today=0}] run function sru_checkin:check_dist
execute as @a[scores={sru_ci_today=1..}] run scoreboard players set @s sru_ci_hist_00 1

# 玩家可用指令入口：/trigger sru_ci_show、/trigger sru_ci_resign、/trigger sru_ci_set_total
scoreboard players enable @a sru_ci_show
scoreboard players enable @a sru_ci_resign
scoreboard players enable @a sru_ci_set_total
execute as @a[scores={sru_ci_show=1..}] run function sru_checkin:command/show
execute as @a[scores={sru_ci_resign=0..30}] run function sru_checkin:command/resign
execute as @a[scores={sru_ci_resign=31..}] run function sru_checkin:command/resign_invalid
execute as @a[scores={sru_ci_set_total=0..}] run function sru_checkin:command/set_total
execute as @a[scores={sru_ci_set_total=..-1}] run function sru_checkin:command/set_total_invalid
