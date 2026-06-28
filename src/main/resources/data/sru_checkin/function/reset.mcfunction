# 兼容旧入口：当前实现不再依赖 daytime 回绕重置。
execute store result score #current_day sru_ci_tmp run time query gametime
scoreboard players operation #current_day sru_ci_tmp /= #ticks_per_day sru_ci_tmp
scoreboard players add #current_day sru_ci_tmp 1
scoreboard players operation #current_day sru_ci_tmp += #day_offset sru_ci_tmp
