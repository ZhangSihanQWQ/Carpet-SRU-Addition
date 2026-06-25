execute store result score #daytime sru_ci_dist run time query daytime
execute if score #daytime sru_ci_dist < #prev_daytime sru_ci_dist run function sru_checkin:reset
scoreboard players operation #prev_daytime sru_ci_dist = #daytime sru_ci_dist

execute as @a unless score @s sru_ci_lastday = #current_day sru_ci_dist run function sru_checkin:player_init

scoreboard players add @a[scores={sru_ci_today=0}] sru_ci_ticks 1

execute as @a[scores={sru_ci_today=0, sru_ci_ticks=6000..}] run function sru_checkin:check_dist

