# 注册签到榜运行时 ID
execute if data storage fzsd:install fzsd.module{scoreboard:1b} run function #fzsd:calculation/highest_id_add_1
execute if data storage fzsd:install fzsd.module{scoreboard:1b} run scoreboard players operation fzsd.module.scoreboard.display.id.checkin_count fzsd.variable.integer = fzsd.module.scoreboard.display.highest_id fzsd.variable.integer

