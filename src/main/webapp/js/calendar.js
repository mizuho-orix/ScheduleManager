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

///////////////////////////////////////////////////////
// 月のドロップダウンメニューを生成
///////////////////////////////////////////////////////

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

////////////////////////////////////////////////////////
// カレンダーにJSONから読み込んだ予定を追加
////////////////////////////////////////////////////////

function renderCalendar(year, month) {
	const firstDay = new Date(year, month, 1).getDay();			// 月初の曜日
	const lastDate = new Date(year, month + 1, 0).getDate();	// 月末日（翌月の0日＝今月の最終日）
	
	// カレンダーの曜日を除いた日付のエリアを取得→内容をクリア
	const tbody = document.querySelector("#calendar tbody");
	tbody.innerHTML = "";
	
	// 新しい行を作成
	let row = document.createElement("tr");

	// 月初の曜日まで空白セルを追加
	for (let i = 0; i < firstDay; i++) {
		row.appendChild(document.createElement("td"));
	}

	// 最終日までのセルを作成する
	for (let dateNum = 1; dateNum <= lastDate; dateNum++) {
		
		// 月末日までのセルを作成して日付を表示
		const cell = document.createElement("td");
		cell.textContent = dateNum;
	
		const ul = document.createElement("ul");
	
		// 引数で受け取った年月+現在ループ中の日付をYYYY-MM-DD形式に変換
		const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(dateNum).padStart(2, '0')}`;
	
		// main.jspの<script>内の変数scheduleList(JSON)から該当日の予定を抽出
		// s(オブジェクト)のdate(日付)がdateStrと一致するものを抽出
		const tasks = scheduleList.filter(s => s.date === dateStr);
				
        // taskの中にJSONで取得された予定が格納されているので
        // プロパティを指定して<li>要素を作成し、ulに追加していく
		tasks.forEach((task, index) => {
			const li = document.createElement("li");

			// li要素に予定名とID属性を設定
			li.textContent = task.name;
			li.id = `task-${task.id}`;

			// データ属性としてIDを持たせることも可能
			// li.dataset.scheduleId = task.id;

			// 作成した<li>をulに追加
			ul.appendChild(li);
		});
	
		// 作成した予定表示用のulをcellに追加
		cell.appendChild(ul);
	
		// 本日の日付であればcellにclass「today」を追加し、
        // 本日の予定をボックスに初期表示する
		if (dateNum === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
			cell.classList.add("today");
            showTasksForDate(year, month, dateNum, ul);
		}
	
		// カレンダーの日付をクリックすると、class="today-task"の
		// ボックス内にタスク予定を表示するイベントを追加
		// （cellをクリック時、=>{}の中身の処理を実行する）
		cell.addEventListener("click", () => {
            showTasksForDate(year, month, dateNum, ul);
        });
	
		// 現在処理中の日付(dateNum)と月初の曜日(firstDay)の合計が7の倍数の時か、
		// dateNumが月末日に達した時は1週間分の処理が終了するので
		// 作成したrowをカレンダーに追加する処理
		row.appendChild(cell);
		if ((dateNum + firstDay) % 7 === 0 || dateNum === lastDate) {

			// 現在の行の変数rowを追加
			tbody.appendChild(row);

			// 次週用に新しい行<tr>を作成
			row = document.createElement("tr");
		}
	}
}


////////////////////////////////////////////////////////
// 指定したカレンダーの日付の予定をtoday-taskボックスに表示
////////////////////////////////////////////////////////

function showTasksForDate(year, month, dateNum, ul) {
	// 予定表示用ボックスの要素を取得して中身をクリア
	const taskBox = document.querySelector(".today-task");
	taskBox.innerHTML = "";

	// 指定したカレンダーの年月日を表示するp要素を作成
	const dayTitle = document.createElement("p");

	// 引数で受け取った年月日をボックスに追加
	dayTitle.textContent = `${year}年${month + 1}月${dateNum}日の予定`;
	taskBox.appendChild(dayTitle);

	// カレンダー内に予定表示用のulが存在した場合、
	// <ul>内の<li>要素today-taskに表示する
	if (ul) {
		// ul内のすべてのli要素を取得
	    const items = ul.querySelectorAll("li");

		// 予定表示用ボックスに追加するul要素を作成
		const newUl = document.createElement("ul");

		// 取得したli要素をループして新しいulに追加
		items.forEach(li => {
			// 予定表示用のli要素とspan要素を作成
			const newLi = document.createElement("li");
			const span = document.createElement("span");

			// span要素にli要素の内容をコピーしてclass="task-box"を追加
			span.textContent = li.textContent;
			span.classList.add("task-box");

			// li要素に元のli要素のIDをコピー
			newLi.id = li.id;

			// task-box内のli要素をクリックした時のイベントを追加する処理
			newLi.addEventListener("click", () => {
				// テスト ///////////////
				console.log("clicked:", newLi.id);
				
				// クリックされたli要素のIDから「task-」を削除し
				// タスクIDを文字列として取得（例：task-5 → 5）
				const taskId = parseInt(newLi.id.replace("task-", ""));

				// JSONに格納されている配列scheduleListから
				// クリックした予定のタスクIDとidが一致するオブジェクトを取得
				//（※taskIdは文字列として取得されているので、JSONファイルの数値型と
				//　合致させる為に数値型に変換する）
				const task = scheduleList.find(s => s.id === taskId);
				
				// 合致したidがあればJSONファイルのnameとcommentをモーダルに追記
				if (task) {
					document.getElementById("modal-task-name").textContent = `予定名：${task.name}`;
					document.getElementById("modal-task-comment").textContent = `備考　：${task.comment || "（なし）"}`;
					// モーダルを画面に表示
					const modal = document.getElementById("modal");
					modal.style.display = "block";
				}
			});

			// ulに作成したliとspanを追加
			newLi.appendChild(span);
			newUl.appendChild(newLi);
		});

		taskBox.appendChild(newUl);

		// const copyUl = ul.cloneNode(true);
		// taskBox.appendChild(copyUl);
	}
}


////////////////////////////////////////////////////////
// カレンダーの年月を変更した時の処理
////////////////////////////////////////////////////////

function updateCalendar() {

    // ドロップダウンメニューで選択された年を取得
    // （取得された値は文字列の為、数値に変換）
    const year = parseInt(yearSelect.value);

    // ドロップダウンメニューで選択された月を取得
    const month = parseInt(monthSelect.value);

    // Ajaxでサーバーから予定データを取得
    // getSchedulesサーブレットに変数yearとmonthを送信
	fetch(`GetScheduleServlet?year=${year}&month=${month + 1}`, {
		credentials: 'include'
		})
		
        // サーブレットからのレスポンスをJSON形式で取得
        .then(response => {
			if (!response.ok) {
				throw new Error(`HTTP error ${response.status}`);
			}
			return response.json();
		})

        	// 取得したJSONデータ（data）の処理
            .then(data => {
				
				// テスト用///////////////////////
				console.log(data);
				
				// サーバーから取得したJSONデータをscheduleListに格納
				// (scheduleListの中にはデータベースのカラム名でプロパティが
				//  格納されているので、JavaScript側の形式に変換する)
				scheduleList = data.map(s => ({
					id: s.id,
					date: s.schedule_Date,
					name: s.schedule_Name,
					comment: s.comment
				}));

				// 引数に選択された年月を渡してカレンダー作成を実行
				renderCalendar(year, month);
            })
	// エラー時の処理
	.catch(error => {
		console.error("スケジュールの取得に失敗しました:", error);
	});
}

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
	
				<form action="AddScheduleServlet" method="post">
					<label for="add-Date">日付を選択：</label>
					<input type="date" name="add-Date"><br>
					<label for="add-ScheduleName">予定内容　：</label>
					<input type="text" name="add-ScheduleName" placeholder="予定を入力"><br>
					<div class="comment-Area">
						<div class="comment">備考　　　：</div>
						<div class="comment-Box"><textarea name="add-Comment"></textarea></div>
					</div>
					<input type="hidden" name="add-UserId" value="${userId}">
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

// 画面外クリックでモーダルを閉じる処理
// 読み込まれた時に一度だけイベントを設定
window.addEventListener("DOMContentLoaded", () => {
	const modal = document.getElementById("modal");
	const closeButton = document.querySelector(".close-button");

	// モーダルの閉じるボタンのクリック処理
	closeButton.addEventListener("click", () => {
		modal.style.display = "none";
	});

	window.addEventListener("click", (event) => {
		if (event.target === modal) {
			modal.style.display = "none";
		}
	});

	// 初期カレンダー表示
	const year = parseInt(yearSelect.value);
	const month = parseInt(monthSelect.value);
	renderCalendar(year, month);

	// 年か月のドロップダウンメニューが変更された時にuddateCalendarを実行
	yearSelect.addEventListener("change", updateCalendar);
	monthSelect.addEventListener("change", updateCalendar);
});



// 初期表示（引数は今日の年、今日の月）
//renderCalendar(today.getFullYear(), today.getMonth());




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


