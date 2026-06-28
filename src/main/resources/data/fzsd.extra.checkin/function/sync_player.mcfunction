# 同步单个玩家的签到分数到 FZ 榜单。
scoreboard players add @s sru_ci_total 0
execute if data storage fzsd:install fzsd.extra{checkin:1b} run scoreboard players operation @s fzsd.module.scoreboard.display.checkin_count = @s sru_ci_total
