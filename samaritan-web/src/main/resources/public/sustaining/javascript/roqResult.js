







function draw_roqNoneZeroPieChart(  ){


	$.ajax({url: "/sustaining/checkMissingSkus/getResultHeader",
		success: function(header, status){
			console.log("headerColumns: " + header + "\nStatus: " + status);


			$.ajax({url: "/sustaining/checkMissingSkus/getResultContent/" + "testWorkflowId",
				success: function(data, status){
					console.log("Data: " + data.length + "\nStatus: " + status);


					var skuCheckDataJson = JSON.parse(data);

					create_SkuAnalzeGrid(header, skuCheckDataJson.detailDatas )

					showPieChart( skuCheckDataJson.distribute )

				}});
		}});






}
