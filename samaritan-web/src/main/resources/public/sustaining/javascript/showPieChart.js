


function showPieChart(distribute) {


	var myChart = echarts.init(document.getElementById('roqNoneZeroPieChart'));


	var option = {

		tooltip : {
			trigger: 'item',
			formatter: "{b}"
		},
		legend: {
			orient: 'vertical',
			left: '10px',
			top: '15px',
			show : true,
			radius: '25%',
			data: ['missing In Tip', 'missing In Forecast', 'missing In VendorData']
		},
		backgroundColor: '#f7f7f7',
		series : [
			{
				name: 'Roq Value Distribution',
				type: 'pie',
				radius: ['0', '60%'],
				center: ['65%', '50%'],

				label : {
					normal: {
						show : true,
						position : 'inner'

					},
					emphasis : {
						show : false
					}
				},

				data: [
					{value: distribute.missingInTip, name:'missing In Tip'},
					{value: distribute.missingInForecast, name:'missing In Forecast'},
					{value: distribute.missingInVendorData, name:'missing In VendorData'},
				]

				// chartData.series_data
			}
		]
	};


	// show the Chart
	myChart.setOption(option);

	/*
	 {value: distribute.missingInRoqResult, name:'missing In RoqResult'},
	 {value: distribute.countIsAppendSku, name:'Is Append SKU'},
	 */


}