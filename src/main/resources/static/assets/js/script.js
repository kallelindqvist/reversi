$(function() {

	// This demo depends on the canvas element
	if (!('getContext' in document.createElement('canvas'))) {
		alert('Sorry, it looks like your browser does not support canvas!');
		return false;
	}

	var doc = $(document), win = $(window), canvas = $('#board'), ctx = canvas[0]
			.getContext('2d')
	canvas[0].width = win[0].innerWidth;
	canvas[0].height = win[0].innerHeight;
	init()

	function init() {

		var boardSize = 8
		var rectSize = Math.min(canvas.width(), canvas.height()) * .8
				/ boardSize
		var startX = canvas.width() * .25;
		var startY = canvas.height() * .1;
		drawBoard(boardSize, ctx, canvas, startX, startY, rectSize);

		$.get("/state", function(gameState) {
			drawInformation(ctx, canvas, rectSize, startX, startY - rectSize,
					startY + (rectSize * (boardSize + 1)), gameState);
		});

		var turn = 0
		var nrOfClicks = 0;
		var players = [ "Circle", "Cross" ]
		canvas.click(function(e) {
			var x = e.clientX
			var y = e.clientY
			if (x < startX || x > (startX + rectSize * boardSize) || y < startY
					|| y > (startY + rectSize * boardSize)) {
				return

			}
			x -= startX
			y -= startY

			var xCell = parseInt(x / rectSize);
			var yCell = parseInt(y / rectSize);
			var gameOver = false;
			$.ajax({
				type : 'POST',
				url : "/place",
				data : {
					x : xCell,
					y : yCell
				},
				success : function(gameState) {
					if (gameState.gameOver) {
						$.post("/reset");
					} else {
						turn ^= 1
						ctx.clearRect(0, 0, canvas.width(), canvas.height());
						drawBoard(boardSize, ctx, canvas, startX, startY,
								rectSize);
						drawInformation(ctx, canvas, rectSize, startX, startY
								- rectSize, startY
								+ (rectSize * (boardSize + 1)), gameState)
					}
				}
			});

		});
	}

	function drawInformation(ctx, canvas, rectSize, startX, startY, bottomY,
			gameState) {
		ctx.font = "45px Verdana";
		ctx.fillText("current turn: " + gameState.currentTurn, startX, startY);
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
		$.get("/board", function(data) {
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
