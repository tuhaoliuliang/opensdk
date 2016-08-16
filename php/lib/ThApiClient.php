<?php
/**
 * @require PHP>=5.3
 */
require_once __DIR__ . '/ThApiProtocol.php';
require_once __DIR__ . '/SimpleHttpClient.php';

class ThApiClient {
	const VERSION = '1.0';

	private static $apiEntry = 'https://open.tuhaoliuliang.cn/api/entry';

	private $appId;
	private $appSecret;
	private $format = 'json';
	private $signMethod = 'md5';

	public function __construct($appId, $appSecret) {
		if ('' == $appId || '' == $appSecret) throw new Exception('appId 和 appSecret 不能为空');

		$this->appId = $appId;
		$this->appSecret = $appSecret;
	}

	public function get($method, $params = array()) {
		return $this->parseResponse(
			SimpleHttpClient::get(self::$apiEntry, $this->buildRequestParams($method, $params))
		);
	}

	public function post($method, $params = array()) {
		return $this->parseResponse(
			SimpleHttpClient::post(self::$apiEntry, $this->buildRequestParams($method, $params))
		);
	}



	public function setFormat($format) {
		if (!in_array($format, ThApiProtocol::allowedFormat()))
			throw new Exception('设置的数据格式错误');

		$this->format = $format;

		return $this;
	}

	public function setSignMethod($method) {
		if (!in_array($method, ThApiProtocol::allowedSignMethods()))
			throw new Exception('设置的签名方法错误');

		$this->signMethod = $method;

		return $this;
	}



	private function parseResponse($responseData) {
		$data = json_decode($responseData, true);
		if (null === $data) throw new Exception('response invalid, data: ' . $responseData);
		return $data;
	}

	private function buildRequestParams($method, $apiParams) {
		if (!is_array($apiParams)) $apiParams = array();
		$pairs = $this->getCommonParams($method);
		foreach ($apiParams as $k => $v) {
			if (isset($pairs[$k])) throw new Exception('参数名冲突');
			$pairs[$k] = $v;
		}

		$pairs[ThApiProtocol::SIGN_KEY] = ThApiProtocol::sign($this->appSecret, $pairs, $this->signMethod);
		return $pairs;
	}

	private function getCommonParams($method) {
		$params = array();
		$params[ThApiProtocol::APP_ID_KEY] = $this->appId;
		$params[ThApiProtocol::METHOD_KEY] = $method;
		$params[ThApiProtocol::TIMESTAMP_KEY] = date('Y-m-d H:i:s');
		$params[ThApiProtocol::FORMAT_KEY] = $this->format;
		$params[ThApiProtocol::SIGN_METHOD_KEY] = $this->signMethod;
		$params[ThApiProtocol::VERSION_KEY] = self::VERSION;
		return $params;
	}
}
