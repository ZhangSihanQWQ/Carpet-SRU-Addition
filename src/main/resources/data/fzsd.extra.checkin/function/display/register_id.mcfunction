# 注册签到榜运行时 ID；只在已经有签到数据时参与轮播，避免空榜。
execute if data storage fzsd:install fzsd.extra{checkin:1b} if score fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general matches 1.. run function #fzsd:calculation/highest_id_add_1
execute if data storage fzsd:install fzsd.extra{checkin:1b} if score fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general matches 1.. run scoreboard players operation fzsd.module.scoreboard.display.id.checkin_count fzsd.variable.integer = fzsd.module.scoreboard.display.highest_id fzsd.variable.integer
