scoreboard players set @s sru_ci_today 1
scoreboard players add @s sru_ci_total 1
execute if data storage fzsd:install fzsd.module{scoreboard:1b} run function fzsd.extra.checkin:sync_player
tellraw @s ["",{"text":"[系统] ","color":"gold"},{"text":"你已完成今日签到！当前累计签到天数：","color":"green"},{"score":{"name":"@s","objective":"sru_ci_total"},"color":"aqua"}]

