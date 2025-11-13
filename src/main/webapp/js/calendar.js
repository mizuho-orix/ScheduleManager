'use strict';

////////////////////////////////////////////////////////
// カレンダーを表示
////////////////////////////////////////////////////////

// htmlのID「year」要素を取得する
const yearSelect = document.getElementById("year");

// htmlのID「month」要素を取得する
const monthSelect = document.getElementById("month");

// 日付を格納する変数を作成
const today = new Date();

// 年と月の選択肢を生成
// yを2000から始めて2030までループ
for (let y = 2000; y <= 2030; y++) {

    // ドロップダウンメニューの選択肢を格納する変数
    const option = document.createElement("option");

    // フォーム送信時に送られる値
    option.value = y;

    // 画面上に表示される文字
    option.textContent = y;

    // yのループ中に現在の年になったら、その年が選ばれている状態にする
    if (y === today.getFullYear()) {

        // option(選択肢)の状態をtrueに
        option.selected = true;
    }

    // htmlのID「year」に変数「option」の要素を追加する
    yearSelect.appendChild(option);
    
}

// 0～11まで繰り返して月の選択肢を表示するドロップダウンメニューを作成
for (let m = 0; m < 12; m++) {

    // ドロップダウンメニューの選択肢を格納する変数
    const option = document.createElement("option");

    // フォーム送信時に送られる値
    option.value = m;

    // 画面上に表示される文字
    option.textContent = `${m + 1}月`;

    // mのループ中に現在の月になったら、その月が選ばれている状態にする
    if (m === today.getMonth()) {
        // option(選択肢)の状態をtrueに
        option.selected = true;
    }

    //htmlのID「month」に変数「option」の要素を追加する
    monthSelect.appendChild(option);
}

// カレンダーの作成
function renderCalendar(year, month) {

    // getDayメソッドで今月の月初の曜日を取得（0:日曜～6:土曜)
    const firstDay = new Date(year, month, 1).getDay();

    // 今月の月末日を格納
    const lastDate = new Date(year, month + 1, 0).getDate();

    // htmlのID「calendar」の中の「tbody」タグを取得
    // (ここにカレンダーの中身を追加していく)
    const tbody = document.querySelector("#calendar tbody");

    // 「tbody」タグ内の内容を削除
    // （年月変更時にカレンダーを削除するため)
    tbody.innerHTML = "";

    // 新しい<tr>行を作成する
    let row = document.createElement("tr");

    // firstDayに格納された月初の曜日が来るまで
    // 空のデータを追加していく
    for (let i = 0; i < firstDay; i++) {
        // <tr>の中に<td>を追加
        row.appendChild(document.createElement("td"));
    }

    // dateNumが月末の日付になるまで処理を繰り返す
    for (let dateNum = 1; dateNum <= lastDate; dateNum++) {

        // 新しい日付のセル<td>を変数cellとして作成
        const cell = document.createElement("td");

        // 作成したcellに日付を入力
        cell.textContent = dateNum;

        // 予定表示用のリストulを作成して<td>に追加
        const ul = document.createElement("ul");
        cell.appendChild(ul);

        // 予定内容の項目<li>を作成
        let li = document.createElement("li");

        // <li>の内容を入力して<ul>に追加
        li.textContent = "12:00~ 東京ビッグサイト　展示会";
        ul.appendChild(li);

        // 作成したcellが本日の日付であった場合
        // cell(<td>タグ)にclass「today」を追加する
        if (dateNum === today.getDate() &&
            month === today.getMonth() &&
            year === today.getFullYear()
        ) {
            cell.classList.add("today");
        }

        // カレンダーの日付をクリックすると、class="today-task"の
        // ボックス内にタスク予定を表示するイベントを追加
        // （cellをクリック時、=>{}の中身の処理を実行する）
        cell.addEventListener("click", () => {
            const taskBox = document.querySelector(".today-task");

            // taskBoxの中身をクリア
            taskBox.innerHTML = "";

            // クリックしたカレンダーの日付を表示today-taskのボックスに表示
            const dayTitle = document.createElement("p");
            dayTitle.textContent = `${year}年${month + 1}月${dateNum}日の予定`;
            taskBox.appendChild(dayTitle);

            // クリックしたカレンダーのulを取得
            const calendarUl = cell.querySelector("ul");

            // ulが存在した時（calendarUl=true）
            // <ul>内の<li>等の子要素を含めて複製して、today-taskに追加表示する
            if (calendarUl) {
                const copyUl = calendarUl.cloneNode(true); //appendChildだとカレンダーから消えるのでNG
                taskBox.appendChild(copyUl);
            }
        });

        // <tr>の中に、<td>として作成した変数cellを追加
        row.appendChild(cell);

        // 現在処理中の日付(dateNum)と月初の曜日(firstDay)の合計が7の倍数の時か、
        // dateNumが月末日に達した時は1週間分の処理が終了するので
        // 作成したrowをカレンダーに追加する処理
        if ((dateNum + firstDay) % 7 === 0 || dateNum === lastDate) {

            // 現在の行の変数rowを追加
            tbody.appendChild(row);

            // 次週用に新しい行<tr>を作成
            row = document.createElement("tr");
        }
    }
}

// カレンダーの選択肢を変更した時の処理
function updateCalendar() {

    // ドロップダウンメニューで選択された年を取得
    // （取得された値は文字列の為、数値に変換）
    const year = parseInt(yearSelect.value);

    // ドロップダウンメニューで選択された月を取得
    const month = parseInt(monthSelect.value);

    // 引数に選択された年月を渡してカレンダー作成を実行
    renderCalendar(year, month);
}

// 年か月のドロップダウンメニューが変更された時にuddateCalendarを実行
yearSelect.addEventListener("change", updateCalendar);
monthSelect.addEventListener("change", updateCalendar);

// 初期表示（引数は今日の年、今日の月）
renderCalendar(today.getFullYear(), today.getMonth());


// ↑↑ カレンダー表示ここまで ↑↑
////////////////////////////////////////////////////////////////////




////////////////////////////////////////////////////////////////////
// 新規予定作成ボタンをクリック時
////////////////////////////////////////////////////////////////////

$(document).ready(function() {
    $('#add-task').on('click', function() {
    // すでに .addBox が存在するかチェック
    if ($('.addBox').length === 0) {
        // class="today-task"の要素を取得
        const taskBox = document.querySelector(".today-task");

        // today-taskボックスの中身をクリア
        taskBox.innerHTML = "";    

        const addBoxHtml = `
        <div class="addBox">
            <p>新規予定入力</p>
            <form action="#" method="post">
            <label for="add-Date">日付を選択：</label>
            <input type="date" name="add-Date"><br>
            <label for="add-Schedule">予定内容　：</label>
            <input type="text" name="add-Schedule" placeholder="予定を入力"><br>
            <div class="comment-Area">
                <div class="comment">備考　　　：</div>
                <div class="comment-Box"><textarea name="add-Comment"></textarea></div>
            </div>
            <button type="submit">予定を追加</button>
            </form>
        </div>
        `;
        $('.today-task').append(addBoxHtml);
    }
    });
});

// ↑↑ 新規予定作成ボタンクリック時 ここまで ↑↑
////////////////////////////////////////////////////////////////////




// ↓jQueryで実装したのでボツになった

// 画面読み込み時にイベントを作成
// document.addEventListener("DOMContentLoaded", () => {
//     // 予定作成ボタンのID「add-task」の要素を取得
//     const addTaskBtn = document.getElementById("add-task");

//     // class="today-task"の要素を取得
//     const taskBox = document.querySelector(".today-task");

//     // 新規予定入力ボタンを押した時のイベントを作成
//     addTaskBtn.addEventListener("click", () => {
//         // today-taskボックスの中身をクリア
//         taskBox.innerHTML = "";

//         // ボックス内に「新規予定入力」表示
//         const title = document.createElement("h2");
//         title.textContent = "新規予定入力";
//         taskBox.appendChild(title);

//         // titleの下に入力内容をまとめる用のdivを作成
//         const addBox = document.createElement("div");
//         taskBox.appendChild(addBox);
//         addBox.classList.add("addBox");

//         // divの中に作成するulを作成
//         const addBoxUl = document.createElement("ul");
//         addBox.appendChild(addBoxUl);

//         // 10/31 ここで終了

//         // 日付入力フォームのラベルと入力ボックス作成
//         const dateLabel = document.createElement("label");
//         dateLabel.textContent = "日付を選択：";
//         const dateInput = document.createElement("input");
//         dateInput.type = "date"; // type="date"でブラウザ標準のカレンダーUIが使える

//         // 予定入力フォームのラベルとテキストボックス作成
//         const taskLabel = document.createElement("label");
//         taskLabel.textContent = "予定内容：";
//         const taskInput = document.createElement("input");
//         taskInput.type = "text";
//         taskInput.placeholder ="予定を入力";

//         // 追加ボタン作成
//         const addButton = document.createElement("button");
//         addButton.textContent = "追加";

//         // 要素をまとめてdiv内に追加
//         addBox.appendChild(dateLabel);
//         addBox.appendChild(dateInput);
//         addBox.appendChild(document.createElement("br")); //改行
//         addBox.appendChild(taskLabel);
//         addBox.appendChild(taskInput);
//         addBox.appendChild(document.createElement("br")); //改行
//         addBox.appendChild(addButton);

//         // ボタンのクリック処理（ここで予定を保存するなどの処理を追加可能）
//         addButton.addEventListener("click", () => {
//             // 日付入力フォームの値を取得
//             const selectedDate = dateInput.value;

//             // 予定内容入力フォームの値を取得
//             const taskText = taskInput.value.trim();

//             // 日付と予定内容が入力されていれば登録
//             if (selectedDate && taskText) {
//                 const confirmMsg = document.createElement("p");
//                 confirmMsg.textContent = `${selectedDate} に予定を追加しました：${taskText}`;
//                 taskBox.appendChild(confirmMsg);

//                 // 入力欄をクリア
//                 taskInput.value = "";
//             }
//         });
//     });
// });


