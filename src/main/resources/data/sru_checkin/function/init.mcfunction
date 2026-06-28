scoreboard objectives add sru_ci_total dummy "签到天数"
scoreboard objectives add sru_ci_today dummy
scoreboard objectives add sru_ci_ticks dummy
scoreboard objectives add sru_ci_walk minecraft.custom:walk_one_cm
scoreboard objectives add sru_ci_sprt minecraft.custom:sprint_one_cm
scoreboard objectives add sru_ci_w_base dummy
scoreboard objectives add sru_ci_s_base dummy
scoreboard objectives add sru_ci_dist dummy
scoreboard objectives add sru_ci_lastday dummy
scoreboard objectives add sru_ci_tmp dummy

scoreboard players set #ticks_per_day sru_ci_tmp 1728000
scoreboard players add #day_offset sru_ci_tmp 0
scoreboard players set #move_need sru_ci_tmp 20000
execute store result score #current_day sru_ci_tmp run time query gametime
scoreboard players operation #current_day sru_ci_tmp /= #ticks_per_day sru_ci_tmp
scoreboard players add #current_day sru_ci_tmp 1
scoreboard players operation #current_day sru_ci_tmp += #day_offset sru_ci_tmp

scoreboard objectives add sru_ci_show trigger
scoreboard objectives add sru_ci_resign trigger
scoreboard objectives add sru_ci_set_total trigger
scoreboard objectives add sru_ci_gap dummy
scoreboard objectives add sru_ci_hist_00 dummy
scoreboard objectives add sru_ci_hist_01 dummy
scoreboard objectives add sru_ci_hist_02 dummy
scoreboard objectives add sru_ci_hist_03 dummy
scoreboard objectives add sru_ci_hist_04 dummy
scoreboard objectives add sru_ci_hist_05 dummy
scoreboard objectives add sru_ci_hist_06 dummy
scoreboard objectives add sru_ci_hist_07 dummy
scoreboard objectives add sru_ci_hist_08 dummy
scoreboard objectives add sru_ci_hist_09 dummy
scoreboard objectives add sru_ci_hist_10 dummy
scoreboard objectives add sru_ci_hist_11 dummy
scoreboard objectives add sru_ci_hist_12 dummy
scoreboard objectives add sru_ci_hist_13 dummy
scoreboard objectives add sru_ci_hist_14 dummy
scoreboard objectives add sru_ci_hist_15 dummy
scoreboard objectives add sru_ci_hist_16 dummy
scoreboard objectives add sru_ci_hist_17 dummy
scoreboard objectives add sru_ci_hist_18 dummy
scoreboard objectives add sru_ci_hist_19 dummy
scoreboard objectives add sru_ci_hist_20 dummy
scoreboard objectives add sru_ci_hist_21 dummy
scoreboard objectives add sru_ci_hist_22 dummy
scoreboard objectives add sru_ci_hist_23 dummy
scoreboard objectives add sru_ci_hist_24 dummy
scoreboard objectives add sru_ci_hist_25 dummy
scoreboard objectives add sru_ci_hist_26 dummy
scoreboard objectives add sru_ci_hist_27 dummy
scoreboard objectives add sru_ci_hist_28 dummy
scoreboard objectives add sru_ci_hist_29 dummy
scoreboard objectives add sru_ci_hist_30 dummy
