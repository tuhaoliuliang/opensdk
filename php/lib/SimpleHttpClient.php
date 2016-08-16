<?php
/**
 * @require curl-extension
 */
class SimpleHttpClient {
	private static $boundary = '';
	
	public static function get($url, $params) {
		$url = $url . '?' . http_build_query($params);
		return self::http($url, 'GET');
	}

	public static function post($url, $params) {
		$headers = array();
		$body = http_build_query($params);
		return self::http($url, 'POST', $body, $headers);
	}

	/**
	 * Make an HTTP request
	 *
	 * @return string API results
	 * @ignore
	 */
	private static function http($url, $method, $postfields = NULL, $headers = array()) {
		$ci = curl_init();
		/* Curl settings */
		curl_setopt($ci, CURLOPT_USERAGENT, 'ThApiSdk Client v0.1');
		curl_setopt($ci, CURLOPT_CONNECTTIMEOUT, 30);
		curl_setopt($ci, CURLOPT_TIMEOUT, 30);
		curl_setopt($ci, CURLOPT_RETURNTRANSFER, TRUE);
		curl_setopt($ci, CURLOPT_ENCODING, "");
		curl_setopt($ci, CURLOPT_SSL_VERIFYPEER, false);
		curl_setopt($ci, CURLOPT_SSL_VERIFYHOST, 2);
		curl_setopt($ci, CURLOPT_HEADER, FALSE);

		switch ($method) {
			case 'POST':
				curl_setopt($ci, CURLOPT_POST, TRUE);
				if (!empty($postfields)) {
					curl_setopt($ci, CURLOPT_POSTFIELDS, $postfields);
				}
				break;
		}

		curl_setopt($ci, CURLOPT_URL, $url );
		curl_setopt($ci, CURLOPT_HTTPHEADER, $headers );
		curl_setopt($ci, CURLINFO_HEADER_OUT, TRUE );

		$response = curl_exec($ci);
		$httpCode = curl_getinfo($ci, CURLINFO_HTTP_CODE);
		$httpInfo = curl_getinfo($ci);

		curl_close ($ci);
		return $response;
	}

	private static function build_http_query_multi($params, $files) {
		if (!$params) return '';

		$pairs = array();

		self::$boundary = $boundary = uniqid('------------------');
		$MPboundary = '--'.$boundary;
		$endMPboundary = $MPboundary. '--';
		$multipartbody = '';

		foreach ($params as $key => $value) {
			$multipartbody .= $MPboundary . "\r\n";
			$multipartbody .= 'content-disposition: form-data; name="' . $key . "\"\r\n\r\n";
			$multipartbody .= $value."\r\n";
		}
		
		$multipartbody .= $endMPboundary;
		return $multipartbody;
	}
}
