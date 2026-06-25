# 同步所有玩家的签到分数到 FZ 榜单
execute if data storage fzsd:install fzsd.module{scoreboard:1b} as @a run function fzsd.extra.checkin:sync_player

