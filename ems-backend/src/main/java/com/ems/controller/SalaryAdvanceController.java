@RestController
@RequestMapping("/api/advances")
@RequiredArgsConstructor
@Tag(name = "Salary Advances", description = "Give advances and track month-end recovery")
public class SalaryAdvanceController {

    private final SalaryAdvanceService advanceService;

    @PostMapping
    public ResponseEntity<AdvanceResponseDTO> giveAdvance(@Valid @RequestBody AdvanceRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(advanceService.giveAdvance(request));
    }

    @GetMapping
    public ResponseEntity<List<AdvanceResponseDTO>> getOutstanding() {
        return ResponseEntity.ok(advanceService.getOutstandingAdvances());
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<AdvanceResponseDTO>> getForEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(advanceService.getOutstandingForEmployee(employeeId));
    }

    @GetMapping("/employee/{employeeId}/total")
    public ResponseEntity<Double> getOutstandingTotal(@PathVariable Long employeeId) {
        return ResponseEntity.ok(advanceService.getOutstandingTotal(employeeId));
    }

    @PostMapping("/{advanceId}/deduct")
    public ResponseEntity<AdvanceResponseDTO> deduct(@PathVariable Long advanceId, @RequestParam double amount) {
        return ResponseEntity.ok(advanceService.recordDeduction(advanceId, amount));
    }
}
