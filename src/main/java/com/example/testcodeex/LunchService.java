package com.example.testcodeex;

public class LunchService {

    private final LunchRepository repository;

    public LunchService(LunchRepository repository) {
        assert repository != null;
        this.repository = repository;
    }

    public Lunch createNewLunch(Lunch lunch) {
        lunch.setType("한식");
        return repository.save(lunch);
    }
}
