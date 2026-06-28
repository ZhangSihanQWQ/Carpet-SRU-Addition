tellraw @s {"text":"修改签到天数参数无效：请使用 /trigger sru_ci_set_total set <目标天数+1>，例如设为0天用 set 1，设为100天用 set 101。","color":"red"}
scoreboard players reset @s sru_ci_set_total
scoreboard players enable @s sru_ci_set_total
