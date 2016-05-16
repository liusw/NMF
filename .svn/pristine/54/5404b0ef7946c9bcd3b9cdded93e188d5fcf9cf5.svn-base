<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en-us">
<head>
<%@ include file="/WEB-INF/jsp/common/meta.jsp"%>
<title>订单查询-数据魔方</title>
</head>
<body>
	<jsp:include page="/WEB-INF/jsp/common/top.jsp">
		<jsp:param name="nav" value="data" />
	</jsp:include>
	
	<jsp:include page="/WEB-INF/jsp/common/dataMenu.jsp">
		<jsp:param name="nav" value="businessData" />
		<jsp:param name="subnav" value="tableData" />
	</jsp:include>

	<div class="main2">
		<jsp:include page="/WEB-INF/jsp/common/topTabMenu.jsp">
			<jsp:param name="tabActive" value="order" />
		</jsp:include>
	
		<jsp:include page="/WEB-INF/jsp/common/mainNav2.jsp" />
		<div class="main2Body">
		 	<jsp:include page="/WEB-INF/jsp/common/tabMenu.jsp">
				<jsp:param name="tab" value="pay" />
				<jsp:param name="subnav" value="order" />
			</jsp:include>
			
			<form class="form-horizontal">
				<div class="form-group">
					<label class="col-md-1 control-label">卡型号: </label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<select id="pcard" class="form-control">
										<option value="0">所有</option>
										<option value="1">铜牌会员</option>
										<option value="2">银牌会员</option>
										<option value="3">金牌会员</option>
										<option value="5">钻石会员</option>
										<option value="6">白金会员</option>
										<option value="7">精英会员</option>
										<option value="8">66大礼包</option>
										<option value="10">紫钻</option>
										<option value="12">蓝钻</option>
										<option value="13">红钻</option>
										<option value="15">黄钻</option>
										<option value="16">花</option>
										<option value="17">束花</option>
										<option value="18">月钻</option>
										<option value="19">月钻*2</option>
										<option value="20">复活券</option>
										<option value="21">复活券*7</option>
										<option value="22">复活券*20</option>
										<option value="38">IFP1</option>
										<option value="39">IFP2</option>
										<option value="9910">(活)紫钻</option>
										<option value="9912">(活)蓝钻</option>
										<option value="9913">(活)红钻</option>
										<option value="9915">(活)黄钻</option>
										<option value="100000">批发</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label"> 付费状态: </label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<select id="firstPay" class="form-control">
									<option value="0">所有</option>
									<option value="1">首付</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label">类型: </label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<select id="ptype" class="form-control">
									<option value="-1">所有</option>
									<option value="1">vip</option>
									<option value="2">钻石</option>
									<option value="3">(活动)钻石</option>
									<option value="4">博雅币</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label">支付方式:</label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<select id="payType" class="form-control">
									<option value="-1">所有</option>
									<option value="0">快钱.</option>
									<option value="1">支付宝</option>
									<option value="2">易宝银行卡</option>
									<option value="3">未知</option>
									<option value="4">漫游支付</option>
									<option value="5">Offerpay</option>
									<option value="6">Social gold</option>
									<option value="7">webatm_NTD</option>
									<option value="8">Paypal</option>
									<option value="9">webatm_MYR</option>
									<option value="10">Babi卡</option>
									<option value="11">MyCard实体卡</option>
									<option value="12">Checkout</option>
									<option value="13">Gash会员</option>
									<option value="14">MyCard银行转账</option>
									<option value="15">MyCard会员</option>
									<option value="16">Gash实体卡</option>
									<option value="17">Paysbuy Counter Service</option>
									<option value="18">null</option>
									<option value="19">null</option>
									<option value="20">null</option>
									<option value="21">MOL-MYR</option>
									<option value="22">MOPAY手机</option>
									<option value="23">MOL-SGD</option>
									<option value="24">MOL-INR</option>
									<option value="25">MOL-PHP</option>
									<option value="26">Paypal dg</option>
									<option value="27">Facebook Credits</option>
									<option value="28">Paypal europe</option>
									<option value="29">payruns</option>
									<option value="30">MOL-THB</option>
									<option value="31">MobileFirst-THB</option>
									<option value="32">MOL-THB-CALL</option>
									<option value="33">MOL-THB-PIN</option>
									<option value="34">MOL-IDR</option>
									<option value="35">Paysbuy Online Banking</option>
									<option value="36">MobileFirst-IDR</option>
									<option value="37">MobileFirst-MYR</option>
									<option value="38">MobileFirst-SGD</option>
									<option value="39">bycard</option>
									<option value="40">zong-IDR</option>
									<option value="41">gudangvoucher</option>
									<option value="42">heisha</option>
									<option value="43">黑鲨-会员</option>
									<option value="44">babi(7-11)</option>
									<option value="45">Indomog</option>
									<option value="46">Indomog实体卡</option>
									<option value="47">新GASH实体卡</option>
									<option value="48">新GASH会员</option>
									<option value="49">Mycard手机市话网路</option>
									<option value="51">OneCard支付</option>
									<option value="56">unipay</option>
									<option value="59">nl支付宝</option>
									<option value="61">eicard</option>
									<option value="62">nl电话卡支付</option>
									<option value="63">cashU</option>
									<option value="64">mol-thb-zest</option>
									<option value="98">特殊活动(ATM、瑞穗牛奶、EPLAY)</option>
									<option value="99">iphone平台支付</option>
									<option value="100">所有的</option>
									<option value="101">plinga</option>
									<option value="102">蜗牛币</option>
									<option value="103">4399游币</option>
									<option value="104">coda</option>
									<option value="105">iPhone支付</option>
									<option value="106">friendster coins</option>
									<option value="107">hi5 coins</option>
									<option value="108">盛大点</option>
									<option value="109">白金币</option>
									<option value="110">6币</option>
									<option value="111">淘点</option>
									<option value="112">woyo币</option>
									<option value="113">android-机锋</option>
									<option value="114">android-91</option>
									<option value="115">遨游币</option>
									<option value="116">麻球 coins</option>
									<option value="117">android-MARKETS</option>
									<option value="118">51币</option>
									<option value="119">支付宝1</option>
									<option value="120">微币</option>
									<option value="121">淘金币</option>
									<option value="122">Q点</option>
									<option value="123">337支付</option>
									<option value="124">爆米花</option>
									<option value="125">酷币</option>
									<option value="126">金币</option>
									<option value="127">元</option>
									<option value="128">元</option>
									<option value="129">天涯贝</option>
									<option value="130">快币</option>
									<option value="131">元</option>
									<option value="132">牛仔金币</option>
									<option value="133">彩贝</option>
									<option value="134">mogapay</option>
									<option value="135">人人豆</option>
									<option value="136">元</option>
									<option value="137">元</option>
									<option value="138">360币</option>
									<option value="139">游币</option>
									<option value="140">元</option>
									<option value="141">元</option>
									<option value="142">元</option>
									<option value="143">元</option>
									<option value="144">金豆</option>
									<option value="145">元</option>
									<option value="146">元</option>
									<option value="147">元</option>
									<option value="148">元</option>
									<option value="149">元</option>
									<option value="150">元</option>
									<option value="151">元</option>
									<option value="152">元</option>
									<option value="153">元</option>
									<option value="154">元</option>
									<option value="155">纵横币</option>
									<option value="999">支付渠道测试</option>
								</select>
							</div>
						</div>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-1 control-label"></label>
					<div class="col-md-11">
						<div class="row">
							<div class="col-md-4">
								<button type="button" class="btn btn-default" id="addtask" data-loading-text="提交中...">提交任务</button>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/common/foot.jsp"%>

	<!-- Bootstrap core JavaScript navbar-fixed-bottom
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="static/js/basic.js"></script>
	<script src="static/js/multiselect.js"></script>
	<script type="text/javascript" src="static/select2/js/select2.min.js"></script>
	<script src="static/js/daterangepicker.js"></script>
	<script src="static/js/daterange.by.js"></script>
	<script src="static/js/common.js"></script>
	<script type="text/javascript">
		var navForm;
		$(function() {
			navForm = navForm.init({'showPlat':true,'showSid':true,'sidType':'MultiSelect','showDate':{'isShow':true},
				platChangeCallback:updateNavMultiSid
			});

			//$("#inputEventCate").selectpicker();
			$("#payType").select2();
		});
		$("#addtask").click(function() {
			var start = navForm.daterange.getStartDate("#navdaterange");
			var end = navForm.daterange.getEndDate("#navdaterange");
			
			if (isEmpty(start) || isEmpty(end)) {
				alert("请选择查询的时间段");
				return;
			}
			$('#queryWarning').show();

			$.getJSON("data/order/orderQuery.htm", {
				plat : $("#navPlat").val(),
				sid : $("#navMultiSid").attr("selectSidValues"),
				bpid : $("#navMultiSid").attr("selectBpidValues"),
				startTime : start,
				endTime : end,
				ptype : $('#ptype').val(),
				payType : $('#payType').val(),
				firstPay : $('#firstPay').val()
			}, function(data) {
				commonDo.addedTaskSuccess(data);
			}, "json").error(function() {
				commonDo.addedTaskError();
			});
		});
	</script>
</body>
</html>
