package kr.ac.kookmin.wink.planlist.user.domain;

public enum LoginType {
    REGISTER,
    EXIST;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}