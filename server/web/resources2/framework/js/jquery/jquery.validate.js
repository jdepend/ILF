(function($) {
	var func = function(obj, bopDom) {
		this.fc = $(obj);
		this.validateSuccess = false;
		this.rangeType = null;
		this.rangeDom = DISLAND.range_getRangeDom(bopDom);
		this.isRequired = this.rangeDom.attr(DISLAND.BOP_RANGE_REQUIRED);
		this.minLength = this.rangeDom.attr(DISLAND.BOP_RANGE_MINLENGTH);
		this.maxLength = this.rangeDom.attr(DISLAND.BOP_RANGE_MAXLENGTH);

		if (this.rangeDom.children().length > 0) {
			this.rangeType = this.rangeDom.children().get(0).nodeName;
		}

		this._validate = function() {
			// 客户端验证
			var errMsg = this._rangeValidate();
			if (!errMsg) {
				errMsg = this._subRangeValidate();
			}

			// 服务端验证
			if (!errMsg) {
				var serverMsg = this._serverValidate();
				if (typeof (serverMsg) == 'string') {
					errMsg = serverMsg;
				}
			}
			return _validateResult(this.fc, errMsg);
		}

		this._serverValidate = function(errMsg) {
			if (this.rangeType == DISLAND.BOP_RANGE_LOGIC) {
				var data = {};
				var ajaxfc = this.fc;
				var serverValidate = '';

				data.vcId = this.fc.parent().attr(DISLAND.BO_ID);
				data.value = this._getValue();
				data.rangeClass = $(this.rangeDom.children().get(0)).attr(
						DISLAND.BOP_RANGE_LOGIC_CLASS);
				// 远程提交
				$.ajax({
					type : "POST",
					url : appConfig.ctx + actionURL.getFcServerValidate(),
					async : false,
					data : data,
					success : function(msg) {
						errMsg = msg;
					}
				});
			}
			return errMsg;
		}

		this._rangeValidate = function() {
			var valLen = strlen(this._getValue());

			if (this.isRequired == 'true' && !(!!this._getValue()))
				return I18N.RANGE_REQUIRED;

			if (this.maxLength && valLen > this.maxLength)
				return I18N.RANGE_NOT_LONG + this.maxLength
						+ I18N.RANGE_LENGTH_LESS_2;

			if (this.minLength && valLen < this.minLength)
				return I18N.RANGE_NOT_LESS + this.minLength
						+ I18N.RANGE_LENGTH_LESS_2;

			return this.validateSuccess;
		}

		this._subRangeValidate = function() {
			var val = this._getValue(this.fc);
			if (val == '' && this.isRequired != 'true')
				return this.validateSuccess;
			// 连续型
			if (this.rangeType == DISLAND.BOP_RANGE_SEQUENCE) {
				if (isNaN(val))
					return errMsg = I18N.RANGE_NAN;

				var max = this.rangeDom.find(DISLAND.BOP_RANGE_MAX).text();
				var min = this.rangeDom.find(DISLAND.BOP_RANGE_MIN).text();
				var step = this.rangeDom.find(DISLAND.BOP_RANGE_STEP).text();
				var scale = this.rangeDom.find(DISLAND.BOP_RANGE_SCALE).text();
				if (!this._isINSequence(val, max, min, step, scale)) {
					return I18N.RANGE_MUST_IN + min + I18N.RANGE_MUST_IN_TO
							+ max + I18N.RANGE_MUST_IN_END + "，"
							+ I18N.RANGE_MUST_IN_STRP + step;
				}
			}
			// 枚举型
			else if (this.rangeType == DISLAND.BOP_RANGE_ENUM) {
				var result = this.rangeDom.find(DISLAND.BOP_RANGE_ENUM);
				var flag = false;
				result.find(DISLAND.BOP_RANGE_ITEM).each(function() {
					if ($(this).attr(DISLAND.BOP_VALUE) == val) {
						flag = true;
						return false;
					}
				});
				if (!flag)
					return I18N.RANGE_NOTIN_RANGE;
			}
			// 逻辑型
			else if (this.rangeType == DISLAND.BOP_RANGE_LOGIC) {

			}
			return this.validateSuccess;
		}

		this._isINSequence = function(value, max, min, step, scale) {
			if (Number(scale) > 0) {
				var flag = String(value).match(
						"^([/-]?)\\d+(\\.\\d{1," + scale + "})?$");
				if (flag == null)
					return false;
			}
			var lower = NumberUtil.sub(value, min);
			var upper = NumberUtil.sub(value, max);
			if (lower < 0 || 0 < upper)
				return false;
			if (Number(step) == 0)
				return true;
			return NumberUtil.mod(lower, step) == 0;
		}

		this._getValue = function() {
			return this.fc.val();
		}
	};

	/**
	 * 处理错误提示信息
	 */
	function _validateResult(fc, errMsg) {
		if (!!errMsg) {
			$(fc).attr("title", errMsg);
			$(fc).tipsy({
				trigger : 'manual',
				gravity : 'w'
			}).tipsy("show");
			$(fc).addClass("error");
			return false;
		} else {
			$(fc).tipsy({
				trigger : 'manual',
				gravity : 'w'
			}).tipsy("hide");
			$(fc).removeClass("error");
			return true;
		}
	}

	/**
	 * 控件添加监听
	 */
	$.fn.addListen = function(bopDom) {
		if (CommonDom.isTextDom(this)) {
			var f = new func(this, bopDom);
			this.bind("blur", function() {
				f._validate();
			});
		}
	}

	/**
	 * 执行控件验证
	 */
	$.fn.validate = function(bopDom) {
		if (CommonDom.isTextDom(this)) {
			var f = new func(this, bopDom);
			return f._validate();
		}
		return true;
	}
})(jQuery);