/*
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
class RedissonAlertSchedulerTest extends IntegrationHelper {

    @Autowired
    private RedissonAlertScheduler redissonAlertScheduler;

    @Autowired
    private AuditingHandler auditingHandler;

    @Autowired
    private AlertRepository alertRepository;

    @Test
    void 생성된_지_60일을_초과한_알림은_삭제_상태로_된다() {
        // given
        Long memberId = 1L;

        LocalDateTime pastTime = LocalDateTime.now()
                .minusDays(61);
        auditingHandler.setDateTimeProvider(() -> Optional.of(pastTime));
        Alert savedOldAlert = alertRepository.save(옛날_알림_생성());

        auditingHandler.setDateTimeProvider(() -> Optional.of(LocalDateTime.now()));
        Alert savedAlert = alertRepository.save(알림_생성_id_없음());

        // when
        // redissonAlertScheduler.deleteExpiredAlerts(); 24.08.13: 레디스 실행 시 젠킨스에서 발생하는 오류로 인해 우선은 분산 락 호출 검증은 보류합니다.
        alertRepository.deleteExpiredAlerts();

        // then
        Optional<Alert> foundSavedAlert = alertRepository.findByMemberIdAndId(memberId, savedAlert.getId());
        Optional<Alert> foundSavedOldAlert = alertRepository.findByMemberIdAndId(memberId, savedOldAlert.getId());

        assertSoftly(softly -> {
            softly.assertThat(foundSavedAlert).isPresent();
            softly.assertThat(foundSavedOldAlert).isPresent();
            Alert recentAlert = foundSavedAlert.get();
            Alert oldAlert = foundSavedOldAlert.get();
            softly.assertThat(recentAlert.getDeletedAt()).isNull();
            softly.assertThat(oldAlert.getDeletedAt()).isNotNull();
        });
    }

    24.08.13: 레디스 실행 시 젠킨스에서 발생하는 오류로 인해 우선은 분산 락 호출 검증은 보류합니다.
    @Test
    void 분산_락으로_중복호출을_막는다() throws InterruptedException {
        // given
        int numberOfThreads = 5;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        CountDownLatch latch = new CountDownLatch(numberOfThreads);
        AtomicLong atomicLong = new AtomicLong();

        // when
        for (int i = 0; i < numberOfThreads; i++) {
            executorService.submit(() -> {
                try {
                    redissonAlertScheduler.deleteExpiredAlerts();
                    atomicLong.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(40, TimeUnit.SECONDS);
        executorService.shutdown();

        // then
        assertThat(atomicLong.get()).isEqualTo(1);
    }

}
*/
