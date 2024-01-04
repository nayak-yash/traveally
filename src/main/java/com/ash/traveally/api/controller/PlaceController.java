package com.ash.traveally.api.controller;


import com.ash.traveally.api.dto.PlaceDto;
import com.ash.traveally.api.dto.PlaceResponse;
import com.ash.traveally.api.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
public class PlaceController {
    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("place")
    public ResponseEntity<PlaceResponse> getPlaces(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ) {
        return ResponseEntity.ok(placeService.getAllPlace(pageNo, pageSize));
    }

    @GetMapping("place/{placeId}")
    public ResponseEntity<PlaceDto> PlaceDetail(@PathVariable int placeId) {
        return ResponseEntity.ok(placeService.getPlace(placeId));
    }

    @PostMapping("place/create")
    public ResponseEntity<PlaceDto> createPlace(@RequestBody PlaceDto placeDto) {
        return ResponseEntity.ok(placeService.createPlace(placeDto));
    }

    @PutMapping("place/{placeId}/update")
    public ResponseEntity<PlaceDto> update(@RequestBody PlaceDto placeDto, @PathVariable int placeId) {
        return ResponseEntity.ok(placeService.updatePlace(placeDto, placeId));
    }

    @DeleteMapping("place/{placeId}/delete")
    public ResponseEntity<String> delete(@PathVariable int placeId) {
        placeService.deletePlace(placeId);
        return ResponseEntity.ok("Deleted Successfully");
    }
}
