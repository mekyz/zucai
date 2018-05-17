<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<div class="row">
	<div class="btn-toolbar">
		<div class="pull-right" id="div-fuzzy-search">
			<input type="text" placeholder="模糊查询" id="fuzzy-search">
			<div class="btn-group" style="float: none;">
				<button type="button" class="btn" id="btn-simple-search">
					<i class="fa fa-search"></i>
				</button>
				<button type="button" class="btn" title="高级查询" id="toggle-advanced-search">
					<i class="fa fa-angle-double-down"></i>
				</button>
			</div>
		</div>
		<button type="button" class="btn btn-primary pull-right" id="btn-add">
			<i class="fa fa-plus"></i> 添加
		</button>
	</div>
</div>