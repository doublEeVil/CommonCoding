<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>drag file</title>
    <style type="text/css">
		* {
			margin: 0;
			padding: 0;
		}
		.container {
			width: 60%;
			max-width: 600px;
			height: 320px;
			padding: 15px;
			margin: 20px auto 0;
			border-radius: 10px;
			background-color: #fce4ec;
		}
		.dashboard {
			width: 100%;
			height: 100%;
			box-sizing: border-box;
			padding: 12px;
			border: 3px dashed #F8BBD0;
			border-radius: 5px;
			font-size: 20px;
			color: #2c1612;
			cursor: text;
			white-space: pre-wrap;
			/*word-break: break-all;*/
			word-wrap: break-word;
			overflow-y: auto;
		}
		p {
			margin-top:10px;
			color:green;
			text-align:center;
		}
		.btn {
			width: 40%;
			padding: 15px;
			max-width: 600px;
			border-radius: 10px;
			background-color: #fce4ec;
		}

		button {
			font-size: 20px;
			text-align:center;
		}
	</style>
</head>
<body>
<div class="container">
    <div id="dashboard" class="dashboard"><p>拖进文件来此处</p></div>
</div>
<div class='btn'>
    <button>点击上传</button>
</div>
<script type="text/javascript">
		var files = [];
		var dashboard = document.getElementById("dashboard")
		dashboard.addEventListener("dragover", function (e) {
			e.preventDefault()
			e.stopPropagation()
		})
		dashboard.addEventListener("dragenter", function (e) {
			e.preventDefault()
			e.stopPropagation()
		})
		dashboard.addEventListener("drop", function (e) {
			// 必须要禁用浏览器默认事件
			e.preventDefault()
			e.stopPropagation()
			var files = this.files || e.dataTransfer.files
			for (var i = 0; i < files.length; i++) {
				console.log(files[i]);
				dashboard.innerText += (files[i].name + "\n")
			}
			// var reader = new FileReader()
			// reader.readAsText(files[0], 'utf-8')
			// reader.onload = function (evt) {
			// 	var text = evt.target.result
			// 	dashboard.innerText = text
			// }
		})

		function checkFiles() {
			if (files.length == 0) {
				dashboard.innerHTML = '<p>拖进文件来此处</p>';
			}
		}

		//checkFiles();
	</script>
</body>
</html>