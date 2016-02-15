var jQT = new $.jQTouch({
	icon: appConfig.ctx+'/images/mobile/jqtouch.png',
	addGlossToIcon: false,
	startupScreen: 'jqt_startup.png',
	statusBar: 'black',
	preloadImages: [
		appConfig.ctx+'/images/mobile/back_button.png',
		appConfig.ctx+'/images/mobile/back_button_clicked.png',
		appConfig.ctx+'/images/mobile/button_clicked.png',
		appConfig.ctx+'/images/mobile/grayButton.png',
		appConfig.ctx+'/images/mobile/whiteButton.png',
		appConfig.ctx+'/images/mobile/loading.gif'
	]
});      