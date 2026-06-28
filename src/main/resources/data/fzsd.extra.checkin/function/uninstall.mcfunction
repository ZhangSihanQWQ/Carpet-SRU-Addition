# 卸载签到榜扩展。
scoreboard objectives remove fzsd.module.scoreboard.display.checkin_count
team remove fzsd.module.scoreboard.display.checkin_count
data modify storage fzsd:install fzsd.extra.checkin set value 0b
