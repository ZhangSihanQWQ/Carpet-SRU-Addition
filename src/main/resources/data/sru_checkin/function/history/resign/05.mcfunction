scoreboard players set @s sru_ci_gap 0
execute unless score @s sru_ci_hist_05 matches 1.. run scoreboard players set @s sru_ci_gap 1
execute if score @s sru_ci_gap matches 1 run scoreboard players set @s sru_ci_hist_05 1
execute if score @s sru_ci_gap matches 1 run scoreboard players add @s sru_ci_total 1
execute if score @s sru_ci_gap matches 1 if data storage fzsd:install fzsd.extra{checkin:1b} run function fzsd.extra.checkin:sync_all
execute if score @s sru_ci_gap matches 1 run tellraw @s ["",{"text":"补签成功：","color":"green"},{"text":"5 天前","color":"aqua"},{"text":"。累计签到天数：","color":"green"},{"score":{"name":"@s","objective":"sru_ci_total"},"color":"aqua"}]
execute unless score @s sru_ci_gap matches 1 run tellraw @s {"text":"这一天已经签到过了。","color":"yellow"}
