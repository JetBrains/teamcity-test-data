#!/bin/sh

count=0

while [ "$count" -lt $1 ]
do
  case $((RANDOM % 3)) in
        (0) status=INFO
                ;;
        (1) status=WARNING
                ;;
        (2) status=ERROR
                ;;
  esac

  data='Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam ultricies lectus magna, at bibendum erat fringilla at. Nam malesuada enim id lectus congue accumsan id sit amet augue. Suspendisse suscipit vitae sem eget efficitur.'

  part=$(
    printf '%s %s %s %s' \
        $(date '+%Y-%m-%d') \
        $(printf '%02d:%02d:%02d' $((RANDOM % 3 + 9)) $((RANDOM % 60)) $((RANDOM % 60)) ) \
        "$status" \
        "$data"
    )
  printf '%s\n' "$part"
  count=$((count + 1))
done