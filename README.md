# Reporting API Consumer

## Proje Hakkında

Bu proje, external bir API ile iletişim kurarak veri işleme ve raporlama yapan bir middleware uygulamasıdır. Kullanıcı kimlik doğrulama işlemlerini, transaction raporlamalarını ve client bilgisi sorgulamalarını yönetir.

## Kurulum ve Çalıştırma

### 1. Gereksinimler
- **Java 17**: Projenin derlenmesi ve çalıştırılması için gereklidir.
- **Maven**: Maven ile bağımlılıkları yükleyebilirsiniz.
- **Redis**: Redis, token yönetimi için kullanılır.
- **Docker (Opsiyonel)**: Redis sunucusunu Docker ile çalıştırabilirsiniz.

### 2. Projenin Çalıştırılması
1. **Bağımlılıkların Yüklenmesi**
   ```bash
   mvn clean install
   ```

2. **Redis'i Çalıştırma**
   - Docker kullanıyorsanız:
     ```bash
     docker run --name redis-server -d -p 6379:6379 redis
     ```

3. **Uygulamayı Başlatma**
   ```bash
   mvn spring-boot:run
   ```

4. **Heroku Üzerinde Çalıştırma**
   Eğer Heroku'ya deploy etmek istiyorsanız:
   ```bash
   git push heroku main
   ```

## API Endpoints

### Login API
- **Endpoint**: `/api/auth/login`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "email": "user@example.com",
    "password": "password123"
  }
  ```
- **Response**:
  ```json
  {
    "uniqueId": "generated-unique-id",
    "token": "jwt-token"
  }
  ```

- **Hatalı Parametre Durumu**:
  ```json
  {
    "status": 400,
    "error": "Validation error",
    "message": "Email alanı zorunlu."
  }
  ```

### Transaction Report API
- **Endpoint**: `/api/transactions/report`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "uniqueId": "qwert1234
    "fromDate": "2015-01-01",
    "toDate": "2017-01-01"
  }
  ```
- **Response**:
  ```json
  {
    "status": "APPROVED",
    "response": [
      {
        "count": 10,
        "total": 5000,
        "currency": "USD"
      }
    ]
  }
  ```

  ### Transaction List API
- **Endpoint**: `/api/transactions/list`
- **Method**: `POST`
- **Request Body**:
  ```json
  {
    "uniqueId": "qwert1234
    "fromDate": "2015-01-01",
    "toDate": "2017-01-01",
    "merchant": 1,
    "acquirer": 1,
  }
  ```
- **Response**:
  ```json
  {
    "current_page": 1,
    "data": [
        {
            "fx": {
                "merchant": {
                    "originalAmount": 200,
                    "originalCurrency": "USD",
                    "convertedAmount": 180,
                    "convertedCurrency": "EUR"
                }
            },
            "updated_at": "2023-11-28 12:30:00",
            "created_at": "2023-11-28 12:00:00",
            "acquirer": {
                "id": 123,
                "name": "Example Bank",
                "code": "EXB",
                "type": "BANKTRANSFER"
            },
            "transaction": {
                "merchant": {
                    "referenceNo": "trx_20231128120000",
                    "status": "APPROVED",
                    "operation": "DIRECT",
                    "type": "AUTH",
                    "message": "Transaction approved",
                    "customData": null,
                    "created_at": "2023-11-28 12:30:00",
                    "transactionId": "1000001-123456789-001"
                }
            },
            "refundable": true,
            "customerInfo": {
                "billingFirstName": "Alice",
                "billingLastName": "Smith",
                "email": "alice.smith@example.com"
            },
            "merchant": {
                "id": 1,
                "name": "Test Merchant",
                "allowPartialRefund": true,
                "allowPartialCapture": true
            }
        }
    ],
    "first_page_url": "http://sandbox-reporting.rpdpymnt.com/api/v3/transaction/list?page=1",
    "from": 1,
    "next_page_url": "http://sandbox-reporting.rpdpymnt.com/api/v3/transaction/list?page=2",
    "path": "http://sandbox-reporting.rpdpymnt.com/api/v3/transaction/list",
    "per_page": 50,
    "prev_page_url": null,
    "to": 50
  }

  ```

### Client Info API
- **Endpoint**: `/api/clients`
- **Method**: `POST`
- **Request Params**:
  - `clientId`: 12345
- **Response**:
  ```json
  {
    "customerInfo": {
      "id": 1,
      "email": "michael@gmail.com",
      "billingFirstName": "Michael",
      "billingLastName": "Kara",
      "billingCity": "Antalya"
    }
  }
  ```

- **Hatalı Parametre Durumu**:
  ```json
  {
    "status": 400,
    "error": "Bad Request",
    "message": "Geçersiz clientId"
  }
  ```

### Önemli Notlar:
- Redis bağlantısı için `application.properties` dosyasında `spring.redis.host` ayarını kontrol edin.
- External API erişim sorunlarında `CustomExceptionHandler` sınıfı detaylı hata yönetimi sağlar.

## Docker ile Redis Kurulumu
### Windows için
1. Docker Desktop'u [buradan](https://www.docker.com/products/docker-desktop) indirin ve kurun.
2. Docker'ı başlatın ve Redis'i çalıştırın:
   ```bash
   docker run --name redis-server -d -p 6379:6379 redis
   ```

### MacOS için
1. Terminali açın ve Docker'ı kurmak için aşağıdaki komutu çalıştırın:
   ```bash
   brew install --cask docker
   ```
2. Docker'ı başlatın ve Redis'i çalıştırın:
   ```bash
   docker run --name redis-server -d -p 6379:6379 redis
   ```

## Lisans
Bu proje MIT lisansı altındadır.
