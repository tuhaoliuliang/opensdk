<?php
require_once __DIR__ . '/lib/ThApiClient.php';

$appId = ''; // 后台获得
$appSecret = ''; // 后台获得 
$client = new ThApiClient($appId, $appSecret);


$method = 'tuhao.data.charge';
$params = [
	'amount' => '10',
	'area' => '0',
	'mobile' => '13816371786',
	'roam' => '1',
	'effecttime' => '0'
];

echo '<pre>';
var_dump(
	$client->post($method, $params)
);
echo '</pre>';

echo '<pre>';
var_dump(
	$client->get('tuhao.account.balance');
);
echo '</pre>';
