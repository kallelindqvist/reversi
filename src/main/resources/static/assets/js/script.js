function reset() {
	$.post({
		url : "reset",
		success : function() {
			location.reload();
		}
	});
}

$(function() {

	// This demo depends on the canvas element
	if (!('getContext' in document.createElement('canvas'))) {
		alert('Sorry, it looks like your browser does not support canvas!');
		return false;
	}

	var canvas = document.getElementById("board");
	var ctx = canvas.getContext('2d')
	// canvas[0].width = win[0].innerWidth;
	// canvas[0].height = win[0].innerHeight;
	var rect = canvas.parentNode.getBoundingClientRect();
	canvas.width = rect.width;
	canvas.height = rect.height;
	init()

	function init() {

		var boardSize = 8
		var rectSize = Math.min(canvas.width, canvas.height) * .8 / boardSize
		var startX = canvas.width * .5 - (rectSize * (boardSize / 2));
		var startY = 0;// canvas.height * .1;
		drawBoard(boardSize, ctx, canvas, startX, startY, rectSize);

		var turn = 0
		var nrOfClicks = 0;
		var players = [ "Circle", "Cross" ]
		canvas.addEventListener('click', function(e) {
			var x = e.clientX
			var y = e.clientY
			if (x < startX || x > (startX + rectSize * boardSize) || y < startY
					|| y > (startY + rectSize * boardSize + canvas.offsetTop)) {
				return

			}
			x -= startX
			y -= canvas.offsetTop;

			var xCell = parseInt(x / rectSize);
			var yCell = parseInt(y / rectSize);
			var gameOver = false;
			$.ajax({
				type : 'POST',
				url : "place",
				data : {
					x : xCell,
					y : yCell
				},
				success : function(gameState) {
					if (gameState.gameOver) {
						$.post("reset");
					} else {

						ctx.fillStyle = "#0a870a";
						ctx.fillRect(startX, startY, rectSize * boardSize,
								rectSize * boardSize);
						drawBoard(boardSize, ctx, canvas, startX, startY,
								rectSize);
					}
				},
				complete : function() {
					$.get("state", function(gameState) {
						$("#turn").text(gameState.currentTurn + "s turn")
						$("#black_score").text(
								"Black score: " + gameState.blackScore)
						$("#white_score").text(
								"White score: " + gameState.whiteScore)
						$("#moves").text(gameState.moveHistory)

						if (gameState.currentTurn === 'WHITE') {
							window.setTimeout(function() {
								$.ajax({
									type : "GET",
									url : "move",
									success : function(gameState) {
										$("#turn").text(
												gameState.currentTurn
														+ "s turn")
									},
									complete : function() {
										drawBoard(boardSize, ctx, canvas,
												startX, startY, rectSize);
									}

								})
							}, 1000)
						}
						;
					});
				}
			});
		});
	}

	function drawInformation(ctx, canvas, rectSize, startX, startY, bottomY,
			gameState) {
		ctx.font = "45px Verdana";
		ctx.fillText("current turn: " + gameState.currentTurn, startX, startY
				+ rectSize);
		ctx.fillText("Black: " + gameState.blackScore + " White: "
				+ gameState.whiteScore, startX, bottomY);

		for (var i = 0; i < gameState.currentlyPossibleMoves.length; i++) {
			drawCircle(gameState.currentlyPossibleMoves[i].x,
					gameState.currentlyPossibleMoves[i].y + 1, rectSize,
					startX, startY, 'green')
		}

		ctx.stroke(new Path2D);
	}

	function drawBoard(boardSize, ctx, canvas, startX, startY, rectSize) {
		var path = new Path2D;
		board = new Array(boardSize);
		$.get("board", function(data) {
			board = data;
			for (i = 0; i < boardSize; i++) {
				for (j = 0; j < boardSize; j++) {
					path.rect(startX + rectSize * i, startY + rectSize * j,
							rectSize, rectSize);
					if (board[j][i] == 1) {
						drawCircle(i, j, rectSize, startX, startY, 'black')
					} else if (board[j][i] == 2) {
						drawCircle(i, j, rectSize, startX, startY, 'white')
					}
				}
			}
			ctx.stroke(path);
		})
	}

	function drawCircle(x, y, rectSize, startX, startY, color) {
		var path = new Path2D
		var circleSize = rectSize / 2
		ctx.beginPath();
		ctx.arc(startX + (rectSize * x) + circleSize, startY + (rectSize * y)
				+ circleSize, circleSize, 0, 2 * Math.PI)

		ctx.fillStyle = color
		ctx.fill()
		ctx.stroke();

	}
});
