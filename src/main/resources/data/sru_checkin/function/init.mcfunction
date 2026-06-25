scoreboard objectives add sru_ci_total dummy "签到次数"
scoreboard objectives add sru_ci_today dummy
scoreboard objectives add sru_ci_ticks dummy
scoreboard objectives add sru_ci_walk custom:walk_one_cm
scoreboard objectives add sru_ci_sprt custom:sprint_one_cm
scoreboard objectives add sru_ci_w_base dummy
scoreboard objectives add sru_ci_s_base dummy
scoreboard objectives add sru_ci_dist dummy
scoreboard objectives add sru_ci_lastday dummy

execute unless score #current_day sru_ci_dist matches -2147483648.. run scoreboard players set #current_day sru_ci_dist 1

