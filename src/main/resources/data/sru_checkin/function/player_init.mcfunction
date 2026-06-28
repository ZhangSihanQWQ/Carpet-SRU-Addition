scoreboard players add @s sru_ci_lastday 0
scoreboard players operation @s sru_ci_gap = #current_day sru_ci_tmp
scoreboard players operation @s sru_ci_gap -= @s sru_ci_lastday
execute if score @s sru_ci_gap matches 1 run function sru_checkin:history/shift/01
execute if score @s sru_ci_gap matches 2 run function sru_checkin:history/shift/02
execute if score @s sru_ci_gap matches 3 run function sru_checkin:history/shift/03
execute if score @s sru_ci_gap matches 4 run function sru_checkin:history/shift/04
execute if score @s sru_ci_gap matches 5 run function sru_checkin:history/shift/05
execute if score @s sru_ci_gap matches 6 run function sru_checkin:history/shift/06
execute if score @s sru_ci_gap matches 7 run function sru_checkin:history/shift/07
execute if score @s sru_ci_gap matches 8 run function sru_checkin:history/shift/08
execute if score @s sru_ci_gap matches 9 run function sru_checkin:history/shift/09
execute if score @s sru_ci_gap matches 10 run function sru_checkin:history/shift/10
execute if score @s sru_ci_gap matches 11 run function sru_checkin:history/shift/11
execute if score @s sru_ci_gap matches 12 run function sru_checkin:history/shift/12
execute if score @s sru_ci_gap matches 13 run function sru_checkin:history/shift/13
execute if score @s sru_ci_gap matches 14 run function sru_checkin:history/shift/14
execute if score @s sru_ci_gap matches 15 run function sru_checkin:history/shift/15
execute if score @s sru_ci_gap matches 16 run function sru_checkin:history/shift/16
execute if score @s sru_ci_gap matches 17 run function sru_checkin:history/shift/17
execute if score @s sru_ci_gap matches 18 run function sru_checkin:history/shift/18
execute if score @s sru_ci_gap matches 19 run function sru_checkin:history/shift/19
execute if score @s sru_ci_gap matches 20 run function sru_checkin:history/shift/20
execute if score @s sru_ci_gap matches 21 run function sru_checkin:history/shift/21
execute if score @s sru_ci_gap matches 22 run function sru_checkin:history/shift/22
execute if score @s sru_ci_gap matches 23 run function sru_checkin:history/shift/23
execute if score @s sru_ci_gap matches 24 run function sru_checkin:history/shift/24
execute if score @s sru_ci_gap matches 25 run function sru_checkin:history/shift/25
execute if score @s sru_ci_gap matches 26 run function sru_checkin:history/shift/26
execute if score @s sru_ci_gap matches 27 run function sru_checkin:history/shift/27
execute if score @s sru_ci_gap matches 28 run function sru_checkin:history/shift/28
execute if score @s sru_ci_gap matches 29 run function sru_checkin:history/shift/29
execute if score @s sru_ci_gap matches 30 run function sru_checkin:history/shift/30
execute if score @s sru_ci_gap matches 31.. run function sru_checkin:history/shift/clear
scoreboard players operation @s sru_ci_lastday = #current_day sru_ci_tmp
scoreboard players set @s sru_ci_today 0
scoreboard players set @s sru_ci_ticks 0
scoreboard players add @s sru_ci_total 0
scoreboard players add @s sru_ci_walk 0
scoreboard players add @s sru_ci_sprt 0
scoreboard players operation @s sru_ci_w_base = @s sru_ci_walk
scoreboard players operation @s sru_ci_s_base = @s sru_ci_sprt
scoreboard players set @s sru_ci_dist 0
