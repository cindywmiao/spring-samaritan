

function create_SkuAnalzeGrid( skuCheckHeader, detailDatas  ){



	$('#skuCheckDetailTable').DataTable( {
		data: detailDatas,
		columns: [
			{ title: "sku", "width": "100"  },
			{ title: "inRoqResult", "width": "30"  },
			{ title: "isAppendSku", width: 30  },
			{ title: "inTip", width: 30  },
			{ title: "inForecast", width: 30  },
			{ title: "inVendorData", width: 30  },

			// TODO
			/*
			{ title: "excludedBySkuLive", width: 30  },
			{ title: "excludedByStatusNormal", width: 30  },
			{ title: "excludedBySkuCategory", width: 30  }
			*/
		]
	} );



}



