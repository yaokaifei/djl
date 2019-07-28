/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package software.amazon.ai.repository;

import java.util.Objects;

class Restriction {

    public static final Restriction EVERYTHING = new Restriction(null, false, null, false);

    private Version lowerBound;
    private boolean lowerBoundInclusive;
    private Version upperBound;
    private boolean upperBoundInclusive;

    public Restriction(
            Version lowerBound,
            boolean lowerBoundInclusive,
            Version upperBound,
            boolean upperBoundInclusive) {
        this.lowerBound = lowerBound;
        this.lowerBoundInclusive = lowerBoundInclusive;
        this.upperBound = upperBound;
        this.upperBoundInclusive = upperBoundInclusive;
    }

    public Version getLowerBound() {
        return lowerBound;
    }

    public boolean isLowerBoundInclusive() {
        return lowerBoundInclusive;
    }

    public Version getUpperBound() {
        return upperBound;
    }

    public boolean isUpperBoundInclusive() {
        return upperBoundInclusive;
    }

    public boolean containsVersion(Version version) {
        if (lowerBound != null) {
            int comparison = lowerBound.compareTo(version);

            if ((comparison == 0) && !lowerBoundInclusive) {
                return false;
            }
            if (comparison > 0) {
                return false;
            }
        }
        if (upperBound != null) {
            int comparison = upperBound.compareTo(version);

            if ((comparison == 0) && !upperBoundInclusive) {
                return false;
            }
            return comparison >= 0;
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Restriction that = (Restriction) o;
        return lowerBoundInclusive == that.lowerBoundInclusive
                && upperBoundInclusive == that.upperBoundInclusive
                && Objects.equals(lowerBound, that.lowerBound)
                && Objects.equals(upperBound, that.upperBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, lowerBoundInclusive, upperBound, upperBoundInclusive);
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();

        buf.append(isLowerBoundInclusive() ? '[' : '(');
        if (getLowerBound() != null) {
            buf.append(getLowerBound().toString());
        }
        buf.append(',');
        if (getUpperBound() != null) {
            buf.append(getUpperBound().toString());
        }
        buf.append(isUpperBoundInclusive() ? ']' : ')');

        return buf.toString();
    }
}