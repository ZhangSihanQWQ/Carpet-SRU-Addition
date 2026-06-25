# 尝试轮播签到榜
execute if score fzsd.module.scoreboard.display.current_id fzsd.variable.integer = fzsd.module.scoreboard.display.id.checkin_count fzsd.variable.integer run function fzsd.extra.checkin:display/set_text

