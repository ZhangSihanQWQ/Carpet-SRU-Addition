execute unless entity @s[tag=sru_ci.admin] run tellraw @s {"text":"你没有修改签到天数权限，需要 sru_ci.admin 标签。","color":"red"}
execute if entity @s[tag=sru_ci.admin] run scoreboard players operation @s sru_ci_total = @s sru_ci_set_total
execute if entity @s[tag=sru_ci.admin] run scoreboard players remove @s sru_ci_total 1
execute if entity @s[tag=sru_ci.admin] if data storage fzsd:install fzsd.extra{checkin:1b} run function fzsd.extra.checkin:sync_all
execute if entity @s[tag=sru_ci.admin] run tellraw @s ["",{"text":"已将累计签到天数设为 ","color":"green"},{"score":{"name":"@s","objective":"sru_ci_total"},"color":"aqua"}]
scoreboard players reset @s sru_ci_set_total
scoreboard players enable @s sru_ci_set_total
