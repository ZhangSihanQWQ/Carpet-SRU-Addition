# 设置签到榜显示文本。颜色使用 green，避开 FZ 默认榜和 extra.bbl 的颜色。
data modify storage fzsd:module fzsd.module.scoreboard.text.checkin_count set value {"text":"签到天数","color":"green"}
scoreboard objectives setdisplay sidebar.team.green fzsd.module.scoreboard.display.checkin_count
scoreboard objectives modify fzsd.module.scoreboard.display.checkin_count displayname {"text":"签到天数","color":"green"}
team modify fzsd.module.scoreboard.display.checkin_count color green
team modify fzsd.module.scoreboard.display.checkin_count displayName {"text":"签到天数","color":"green"}
