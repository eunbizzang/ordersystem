# ordersystem
This is java swing team project.

My role was Login and sales&ranking function.

JAVA、oracle DB、GUIはSWINGを用いた飲食店のオーダー管理システムプロジェクトです。

このプロジェクトではログイン機能と売上＆売上ランキング機能を担当しました。

## login
![login1](https://user-images.githubusercontent.com/70651994/139579609-e9529f96-1da4-4ddc-885b-2cc28ddda82e.jpg)
![login2](https://user-images.githubusercontent.com/70651994/139579602-8adf6762-edb4-4b6e-bdda-9dbe3a3d53f2.jpg)

Check if input data exists in DB.

if there's no data, popup message will appear.

入力されたログインデータがデータベース上にあるか確認し、

データが一致しない場合、エラーメッセージのポップアップが出るように設定しました。

## sales&ranking
![기능1](https://user-images.githubusercontent.com/70651994/139579604-3904e9b3-c232-4413-991f-0b6629024dd1.jpg)
![기능2](https://user-images.githubusercontent.com/70651994/139579608-9bac6150-7839-42e7-99c9-2a1e6c3b1137.jpg)

This page shows sales records(product, price, time, total price) of selected period.

in this function, I used jcalendar component for graphically picking a date.

also, I prepared ranking function which shows ranking of the products during a selected period.

売上ページでは、選択された期間内の売上履歴を表示しました。（商品名、日時、価格、選択された期間内の合計売上金額）。

ここでは、カレンダーcompnentのjcalendarを利用し、日付を選択するように設定しています。

また、商品別ランキングボタンを押すと、選択されていた期間内の商品別販売数量、売上金額が順番に出る機能です。
