execute unless score @s sru_ci_today matches 1.. run scoreboard players add @s sru_ci_total 1
scoreboard players set @s sru_ci_hist_00 1
scoreboard players set @s sru_ci_today 1
execute if data storage fzsd:install fzsd.extra{checkin:1b} run function fzsd.extra.checkin:sync_all
tellraw @s ["",{"text":"[系统] ","color":"gold"},{"text":"你已完成今日签到！当前累计签到天数：","color":"green"},{"score":{"name":"@s","objective":"sru_ci_total"},"color":"aqua"}]
