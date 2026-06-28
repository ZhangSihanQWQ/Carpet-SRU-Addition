# 同步所有玩家的签到分数到 FZ 榜单，并维护总览统计。
scoreboard players set fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general 0
execute if data storage fzsd:install fzsd.extra{checkin:1b} as @a run function fzsd.extra.checkin:sync_player
execute if data storage fzsd:install fzsd.extra{checkin:1b} as @a run function fzsd.extra.checkin:add_to_total
team join fzsd.module.scoreboard.display.checkin_count 总签到天数
scoreboard players operation 总签到天数 fzsd.module.scoreboard.display.general = fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general
scoreboard players operation 总签到天数 fzsd.module.scoreboard.display.checkin_count = fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general
