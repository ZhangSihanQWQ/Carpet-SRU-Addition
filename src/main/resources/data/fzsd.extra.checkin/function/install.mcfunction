# 安装签到榜扩展；作为 FZ 计分板模块扩展接入。
scoreboard objectives add fzsd.module.scoreboard.display.checkin_count dummy
team add fzsd.module.scoreboard.display.checkin_count

function #fzsd.extra.checkin:display/set_text

data modify storage fzsd:install fzsd.extra.checkin set value 1b
function fzsd.extra.checkin:sync_all
