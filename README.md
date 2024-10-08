* Multithreading Theory
- Đa luồng là khả năng của CPU xử lý nhiều xử lý (processes) hoặc nhiều luồng (threads) đồng thời
- Processes là 1 trường hợp thực thi chương trình
  Ví dụ: Mở Paint, Excel, PowerPoint, ... thì hệ điều hành (OS) sẽ chỉ định process riêng biệt cho từng ứng dụng.
  OS sẽ gán thanh ghi (registers) riêng biệt, bộ nhớ stack và bộ nhớ heap cho từng process
  -> Java có thể tạo các process dựa vào class ProcessBuilder
- Thread về cơ bản là 1 light-weight process (process nhẹ)
- Thread là 1 đơn vị thực thi và các luồng phải tồn tại trong các processes nhất định
  -> 1 process có thể có 1 hoặc nhiều threads và mọi thread trong 1 process phải chia sẻ bộ nhớ và tài nguyên của process đó
- Việc tạo thread mới cần ít tài nguyên hơn là process (vì tạo process thì OS cần tạo mới thanh ghi, bộ nhớ stack, bộ nhớ heap nên sẽ tốn tài nguyên hơn)
  -> Lý do được gọi là light-weight process
- Mỗi thread trong process chia sẻ bộ nhớ stack, bộ nhớ heap, ...
  -> Phải xử lý đồng thời các thread vì có thể nội dung của bộ nhớ có thể sẽ không nhất quán -> Xử lý đồng bộ hóa (synchronization)

- Process sẽ xử lý nhiều thread bằng cách sử dụng thuật toán time-slicing
- Thuật toán time-slicing là bộ xử lý thread 1 trong thời gian ngắn, sau đó xử lý thread 2, rồi lại xử lý thread 1, xử lý thread 2, ...
  -> Về cơ bản thì 2 thread đều được thực thi cùng 1 lúc -> Parallelization

- Đa luồng giúp thiết kế các ứng dụng phản hồi nhanh hơn (có thể thực hiện nhiều thao tác cùng 1 lúc)
- Đa luồng giúp sử dụng tài nguyên hiệu quả hơn
- Đa luồng cải thiện hiệu suất vì có thể sử dụng nhiều lõi CPU và chạy các luồng song song

- Các thread thao tác dữ liệu trên cùng 1 vùng bộ nhớ vì cùng thuộc process -> Tất cả các thread đều chia sẻ các nguồn tài nguyên nhất định
  -> Nội dung bộ nhớ có thể sẽ không nhất quán -> Phải xử lý đồng bộ hóa
- Khó trong việc design và test các ứng dụng đa luồng
- Khi có nhiều luồng cùng được 1 bộ xử lý trung tâm duy nhất, mỗi lần chuyển đổi giữa các luồng , bộ xử lý trung tâm phải lưu luồng hiện tại và phải tải luồng khác,..
  -> Việc lặp đi lặp lại quá trình chuyển đổi luồng cực kỳ tốn kém

- Vòng đời của thread :
  -> Khi tạo thread thì ban đầu ở trạng thái new, cho đến khi gọi phương thức start()
  -> Bất cứ khi nào gọi phương thức start() thì thread ở trạng thái active. Có 2 sub-states là runnable và running
  -> Khi call phương thức join() hoặc khi 1 thread đang đợi 1 thread khác chạy xong (call phương thức sleep() hoặc wait()) thì thread ở trạng thái blocked -> NO CPU CYCLES!!!
  -> Khi thread hoàn thành xong nhiệm vụ của mình thì thread ở trạng thái terminated

* Threads Manipulation
  start(), sleep(), join()

- Deamon threads là 1 luồng có mức độ ưu tiên thấp (low priority) chạy ngầm để thực hiện các tác vụ như garbage collection
  -> Deamon threads thường được tạo cho I/O operations hoặc services
- Deamon threads thường bị terminated bởi JVM khi tất cả các workers threads bị chấm dứt
  ---> Điểm khác biệt chính giữa worker threads và deamon threads đó là worker thread không bị chấm dứt , trong khi deamon thread bị chấm dứt bởi JVM

- Thread Scheduler là 1 phần của JVM. Trong 1 process có xử lý nhiều luồng, Thread Scheduler sẽ quyết định luồng nào sẽ được thực hiện bởi CPU
- Thuật toán time-slicing được xử lý bởi Thread Scheduler
- Chúng ta có thể chỉ định giá trị mức độ ưu tiên cho mọi luồng (MIN_PRIORITY là 1, MAX_PRIORITY là 10, mặc định là 5)
- Các luồng có cùng giá trị mức độ ưu tiên đều được thực thi theo nguyên tắc FIFS (first-in-first-served), luồng nào đến trước thì thực hiện trước
- Thread Scheduler lưu trữ các luồng trong queue
- Các luồng có mức độ ưu tiên càng cao thì được thực thi trước các luồng có mức độ ưu tiên thấp hơn nhưng nó phụ thuộc vào hệ điều hành
  (tránh tình trạng bị luồng được ưu tiên cao được thực thi, sau đó xảy ra tình trạng 1 luồng ưu tiên thấp không được thực thi)

- Bộ nhớ Stack để lưu trữ các biến local, các arguments của phương thức.Bộ nhớ Heap để lưu trữ các objects.
- Bộ nhớ Stack nhanh hơn bộ nhớ Heap vì sẽ mất nhiều thời gian hơn để truy cập vào các đối tượng được lưu trữ trên bộ nhớ heap
- Bộ nhớ Stack nhỏ hơn bộ nhớ Heap
- Tất cả các luồng đều có bộ nhớ riêng nhưng tất cả các luồng đều chia sẻ bộ nhớ heap
  -> Mục đích của việc đồng bộ hóa là chia sẻ tài nguyên mà không có luồng nào can thiệp vào nhau

- Monitor lock (hoặc Intrinsic lock) khiến cho không có 2 luồng nào có thể thực thi cùng 1 phương thức đồng bộ hóa (syzchronized) tại cùng 1 thời điểm
- Một luồng giữ monitor lock vào thời điểm nó có được lock và release lock
- Khi một luồng A có monitor lock thì không có luồng B nào khác có cùng monitor lock với luồng A và luồng B đó sẽ bị chặn khi cố gắng lấy lock
  -> Vấn đề mỗi object đều chỉ có 1 monitor lock duy nhất, điều đó có nghĩa là nếu có 2 phương thức đồng bộ hóa độc lập thì các luồng phải chờ nhau để mở lock
- Khi có từ khóa syzchronized thì có nghĩa là luồng đó cần monitor lock
- Sự khác nhau giữa class level locking và object level locking (ClassName.class và this truyền vào phương thức synchronized) (18)
  -> Tham khảo https://www.geeksforgeeks.org/object-level-class-level-lock-java/

- Phương thúc wait() được gọi trên 1 đối tượng trong 1 phương thức synchronized, khi 1 luồng A gọi wait() thì nó giải phóng monitor lock và chờ đợi cho đến khi nhận được
  notify hoặc notifyAll từ 1 luồng B -> Luồng A sẽ bị đưa vào trạng thái chờ và không tham gia vào việc cạnh tranh monitor lock
- Phương thức notify() được gọi trên cùng 1 đối tượng mà luồng đã gọi wait(), luồng A sẽ thoát khỏi trạng thái chờ đợi khi luồng B gọi notify() và luồng A tiếp tục thực thi
  -> Notify chỉ đưa 1 luồng ở trạng thái đang chờ thoát khỏi trạng thái chờ mà không xác định được đó là luồng nào
  (kiểu như nhiệm vụ của t là thức tỉnh 1 trong số chúng m, còn thằng nào thức tỉnh thì t k cần biêt =)))
  -> Khi 1 luồng nhận được notify, nó phải cạnh tranh với các luồng khác để lấy monitor lock và tiếp tục thực thi đồng bộ hóa
- Phương thức notifyAll() thức tỉnh tất cả các luồng đang chờ đợi trên đối tượng đó, đảm bảo tất cả các luồng chờ đợi sẽ được thức tỉnh. Nhưng chỉ có 1 luồng giành được
  monitor lock và tiếp tục thực thi

- Sự khác nhau giữa wait và sleep
+ wait được gọi là object trong khi sleep gọi vào luồng
+ wait có thể bị ngắt quãng (InterruptedException), sleep thì không thể bị ngắt quãng
+ wait và notify phải xảy ra trong 1 khối syzchronized, sleep thì không
+ wait giải phóng monitor lock , sleep thì không

- Trong 1 fair lock, khi nhiều luồng cố gắng nắm giữ lock, hệ thống sẽ xử lý chúng theo thứ tự mà chúng yêu cầu lock, đảm bảo rằng luồng đến trước sẽ được ưu tiên hơn
  so với luồng đến sau
- Fair lock giúp tránh tình trạng 1 luồng luôn chiếm lấy lock và không để cho các luồng khác tiếp cận lock, giúp cải thiện hiệu suất trong 1 số trường hợp

- Locks và Synchronized Blocks
+ Reetrant Lock (Khoá tái nhập) cung cấp hành vi tương tự như các khối Synchronized với các tính năng bổ sung
+ Lock có thể ngăn chặn tình trạng thiếu luồng (lock fair), còn khối synchronized mặc định là lock unfair nên có thể xảy ra tình trạng thiếu luồng
+ Các khối synchronized không cần khối try-catch-finally, locks thì ngược lại
+ Locks có thể kiểm tra xem khóa chỉ định có được giữ hay không bởi reetrant lock, các khối synchronized thì không

* Multithreading concepts
- Một biến volatile sẽ được đọc từ main memory (RAM) , không phải từ cache memory
- Thông thường các biến được lưu trữ vào cache memory vì lý do hiệu suất, việc lấy biến từ cache memory nhanh hơn nhiều so với main memory
  -> Không nên sử dụng từ khóa volatile nếu không thực sự cần thiết (vì nó ngăn chặn lệnh reordering , là 1 kỹ thuật tăng cường hiệu suất)

- Deadlock là tình huống 2 hoặc nhiều luồng đợi mãi mãi do các luồng nắm giữ tài nguyên tài nhau và phải chờ đến khi tài nguyên được nhả ra và không có luồng nào được hoàn thành
+ Deadlock trong csdl xảy ra khi hai hay nhiều transaction đang chờ nhau giải phóng khóa
+ Deadlock hệ thống xảy ra khi 1 process(hoặc luồng) ở trạng thái chờ bởi vì tài nguyên yêu cầu đang bị giữ bởi 1 process(hoặc luồng) khác, process(hoặc luồng) này đang chờ tài
  nguyên khác bị giữ bởi 1 process(hoặc luồng) khác

- Livelock là tình huống các luồng trong hệ thống không bao giờ hoàn thành công việc của mình vì chúng luôn phải chờ đợi hoặc phản ứng với hành động của luồng khác mà không làm
  cho công việc tiến triển

- Làm thế nào để xử lý deadlock và livelock:
+ Đảm bảo rằng 1 luồng không bị chặn vô hạn nếu nó không thể có được 1 khóa nhất định -> Sử dụng tryLock() của interface Lock
+ Đảm bảo mỗi luồng lấy được các lock theo cùng thứ tự để tránh bât cứ sự phụ thuộc tuần hoàn trong việc lấy khóa (cyclic dependency)
+ Livelock có thể được xử lý bằng các phương pháp trên và một số luồng ngẫu nhiên thử lại việc lấy các khóa theo các khoảng thời gian ngẫu nhiên

- AtomicInteger thường được sử dụng khi cần thực hiện các phép toán cơ bản trên 1 biến nguyên thủy 1 cách an toàn mà không cần đồng bộ hóa toàn bộ khối mã

- Semaphore là các biến đơn giản (hoặc là kiểu dữ liệu abstract) được sử dụng để kiểm soát quyền truy cập vào 1 nguồn tài nguyên chung
- Semaphore là bản ghi số lượng đon vị của 1 nguồn tài nguyên cụ thể có sẵn, chúng ta phải đợi đến khi có 1 đơn vị tài nguyên khả dụng
- Semaphore chỉ theo dõi được số lượng tài nguyên đang rảnh chứ k xem được cụ thể là tài nguyên nào đang rảnh
- Semaphore chỉ theo dõi được số lượng tài nguyên đang rảnh chứ k xem được cụ thể là tài nguyên nào đang rảnh
- Semaphore có 2 phương thức quan trọng :
+ acquire(): Phương thức acquire() được sử dụng để yêu cầu giữ 1 permit từ Semaphore. Nếu permit có sẵn, thì luồng tiếp tục được thực thi mà không bị chặn. Còn nếu
  không có permit nào trống, luồng sẽ bị chặn cho đến khi 1 permit trống được giải phóng bởi phương thức release() từ 1 luồng khác
+ release() : Phương thức release() được sử dụng để giải phóng 1 permit về seraphore, khi 1 luồng gọi release() Semaphore sẽ tăng số lượng permit lên. Nếu có các luồng khác
  đang chờ đợi permit (bị chặn bởi acquire()) , 1 luồng trong số đó sẽ được tiếp tục thực thi sau khi permit đã được giải phóng

- Việc tạo ra 1 thread khá tốn kém và mất thời gian vì Java cần phân bổ bộ nhớ cho stack mới, phải có stack mới và phải có cache memory trên từng luồng
  -> Thread pools là 1 nhóm các luồng đã được tạo sẵn và được quản lý để thực hiện các tác vụ. Thread pools có thể tái sử dụng các luồng đã tồn tại, giúp tăng hiệu suất
  và giảm tải hệ thống
- Các kiểu Executors:
+ SingleThreadExecutor là 1 luồng duy nhất có thể thực thi các tiến trình theo cách tuần tự. Mỗi tiến trình được thực thi bởi 1 luồng mới, do đó 1 luồng nhất định
  sẽ thực thi tác vụ đầu tiên. Sau đó luồng này chết và SingleThreadExecutor sẽ tạo 1 luồng mới cho tác vụ tiếp theo
+ FixedThreadPool tạo 1 thread pool với n luồng (thường n là số lượng cores của CPU), nếu có nhiều tác vụ hơn n thì những tác vụ được lưu trữ bằng cấu trúc
  dữ liệu LinkedBlockingQueue
+ CachedThreadPool số lượng luồng không bị giới hạn. Nếu tất cả các luồng đang bận thực hiện 1 số tác vụ và 1 tác vụ mới xuất hiện thì thread pool sẽ tạo
  và thêm 1 luồng mới vào executor -> Nếu 1 luồng không hoạt động trong 60s thì sẽ bị xóa và thường dùng cho những tác vụ song song ngắn
+ ScheduledExecutor giúp thực hiện 1 hoạt động nhất định theo các khoảng thời gian đều đặn hoặc có thể sử dụng executor nếu muốn trì hoãn 1 nhiệm vụ nhất định

- Phương thức shutdown():
+ Phương thức shutdown() được sử dụng để dừng thread pool sau khi tất cả các tác vụ đã được thực thi xong.
+ Khi gọi shutdown(), thread pool sẽ không chấp nhận thêm tác vụ mới nhưng sẽ tiếp tục thực thi các tác vụ đã được đưa vào hàng đợi trước đó.
+ shutdown() không ngắt các luồng đang thực thi, chỉ ngăn chặn thread pool chấp nhận tác vụ mới và chờ đợi cho tất cả các tác vụ hiện tại hoàn thành.
- Phương thức shutdownNow():
+ Phương thức shutdownNow() cũng được sử dụng để dừng thread pool, nhưng nó có thể hủy các tác vụ đang chờ trong hàng đợi và cố gắng dừng ngay lập tức các luồng đang thực thi.
+ Khi gọi shutdownNow(), thread pool sẽ cố gắng hủy tất cả các tác vụ chờ đợi và cố gắng ngắt các luồng đang thực thi bằng cách gửi các tín hiệu ngắt (interrupt signals) đến chúng.
+ shutdownNow() trả về một danh sách các tác vụ chưa được thực thi.

- Interface Runnable thực thi các tác vụ với phương thức run() mà không trả về kết quả, trong khi interface Callable sử dụng phương thức call() để trả về kết quả và xử lý ngoại lệ
- Interface Callable trả về 1 đối tượng Future<T>
- Luồng gọi (calling thread) sẽ bị chặn cho đến khi phương thức call() được thực thi và Future<T> trả về kết quả

- ExecutorService có thể xử lý cả 2 interfaces Runnable và Callable
+ executorService.execute(): Phương thức này thực thi interface Runnable và không trả về giá trị
+ executorService.submit(): Phương thức này thực thi cho cả 2 interface Runnable và Callable

- Latch được sử dụng để đồng bộ hóa 1 hoặc nhiều tác vụ bằng cách bắt buộc các tác vụ đợi đến khi hoàn thành 1 tập hợp những hoạt động được thực hiện bởi các tác vụ khác

- CyclicBarrier được sử dụng trong tình huống muốn tạo 1 nhóm các tác vụ để thực hiện 1 công việc và đợi cho đến khi tất cả được hoàn thành trước khi chuyển sang bước tiếp theo (giống join())
  -> Ví dụ như tải xuống nhiều tập dữ liệu web. Khi chưa tải xong thì không thể làm các nhiệm vụ khác như chuẩn hóa dữ liệu, lọc dữ liệu, ... mà phải đợi cho đến khi từng tệp dữ liệu được tải xuống
- CountDownLatch là sự kiện 1 lần, còn CyclicBarrier có thể được tái sử dụng nhiều lần
  -> new CyclicBarrier(N) -> N luồng sẽ chờ đợi nhau để hoàn thành tác vụ
  -> Không thể tái sử dụng Latches nhưng có thể tái sử dụng CyclicBarrier -> reset()

- Queue có cấu trúc FIFO nhưng không phải là cấu trúc dữ liệu đồng bộ
  -> BlockingQueue là 1 inteface đại diện cho queue an toàn cho luồng, đưa và lấy items từ BlockingQueue
- Ví dụ như 1 luồng đưa items vào queue, 1 luồng khác lấy items từ queue tại cùng 1 thời điểm -> producer-consumer pattern (put() và take())

- DelayQueue giữ các phần tử bên trong cho đến khi 1 độ trễ nhất định đã hết hạn, 1 đối tượng chi có thể lấy từ queue khi độ trễ của nó đã hết hạn
- Không thể lưu trữ items null bên trong queue
- Queue được sắp xếp nên đối tượng ở đầu có độ trễ hết hạn lâu nhất
- Nếu không có delay nào bị hết hạn thì sẽ không có phần tử đầu và poll() sẽ trả về null
- size() trả về count của tất cả items còn hạn và hết hạn

....


- Merge Sort là thuật toán sử dụng phương pháp chia để trị
- Merge Sort có độ phức tạp thuật toán là O(NlogN)
- Merge Sort là thuật toán sắp xếp ổn định, nó duy trì thứ tự tương đối của items với các giá trị bằng nhau
- Merge Sort yêu cầu thêm O(N) bộ nhớ
- Merge Sort thường là lựa chọn tốt cho sắp xếp 1 linked list vì chỉ cần yêu cầu thêm O(1) bộ nhớ

- Fork-Join framework là 1 triển khai của ExecutorService interface
- Fork-Join sử dụng cơ chế chia để trị, chia nhỏ những tác vụ lớn thành những tác vụ nhỏ hơn và cuối cùng kết hợp những giải pháp tác vụ nhỏ để đạt được kết quả cuối cùng
- Điều quan trọng là các tác vụ nhỏ phải độc lập để có thể thực hiện song song
- RecursiveTask<T> trả về kiểu generic T. Tất cả các tác vụ muốn thực thi song song trong lớp con phải ghi đè phương thức compute() và trả về kết quả cho tác vụ con
- RecursiveAction là 1 tác vụ nhưng k trả về giá trị gì
- ForkJoinPool đơn giản là 1 thread pool để thực thi các tác vụ fork-join.
- ForkJoinPool sử dụng thuật toán work-stealing ,1 tác vụ không tương đương với 1 luồng
- ForkJoinPool tạo số lượng luồng cố định, thường là số lượng CPU cores
- Nếu 1 luồng không có tác vụ nào, nó có thể lấy nhiệm vụ từ các luồng nhiều tác vụ hơn vì thế các tác vụ được phân bổ cho các luồng
  -> Fork-Join framework có thể xử lý vấn đề cân bẳng tải 1 cách hiệu quả
- fork() được gọi khi 1 tác vụ chia nhỏ công việc thành các tác vụ con , bắt đầu 1 tác vụ con mới đồng thời tác vụ con mới này chạy song song với tác vụ gốc hoặc tác vụ con khác 1 cách không đồng bộ
- join() sử dụng để kết thúc và lấy kết quả của tác vụ con
- invoke() sử dụng để bắt đầu 1 tác vụ và chờ đợi kết quả đến khi hoàn thành của nó





