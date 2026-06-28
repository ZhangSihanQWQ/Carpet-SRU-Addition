# 将当前玩家签到天数累计到 FZ 总览缓存；由 sync_all 调用。
scoreboard players add @s sru_ci_total 0
scoreboard players operation fzsd.module.scoreboard.total.checkin_count fzsd.module.scoreboard.assign.general += @s sru_ci_total
