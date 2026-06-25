# 设置签到榜显示文本
scoreboard objectives setdisplay sidebar.team.gold fzsd.module.scoreboard.display.checkin_count
scoreboard objectives modify fzsd.module.scoreboard.display.checkin_count displayname {"text":"签到榜","color":"gold"}
team modify fzsd.module.scoreboard.display.checkin_count color gold
team modify fzsd.module.scoreboard.display.checkin_count displayName {"text":"签到榜","color":"gold"}
data modify storage fzsd:module fzsd.module.scoreboard.text.checkin_count set value {"text":"签到榜","color":"gold"}

