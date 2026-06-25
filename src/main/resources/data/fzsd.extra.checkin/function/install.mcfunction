# 安装签到榜扩展
scoreboard objectives add fzsd.module.scoreboard.display.checkin_count dummy
team add fzsd.module.scoreboard.display.checkin_count

data modify storage fzsd:module fzsd.module.scoreboard.text.checkin_count set value {"text":"签到榜","color":"gold"}
scoreboard objectives setdisplay sidebar.team.gold fzsd.module.scoreboard.display.checkin_count
scoreboard objectives modify fzsd.module.scoreboard.display.checkin_count displayname {"text":"签到榜","color":"gold"}
team modify fzsd.module.scoreboard.display.checkin_count color gold
team modify fzsd.module.scoreboard.display.checkin_count displayName {"text":"签到榜","color":"gold"}

data modify storage fzsd:install fzsd.extra.checkin set value 1b
execute as @a run function fzsd.extra.checkin:sync_player

